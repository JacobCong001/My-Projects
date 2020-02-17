import datetime as dt
import matplotlib.pyplot as plt
from matplotlib import style
import pandas as pd
import pandas_datareader.data as web
from sklearn.model_selection import train_test_split
from sklearn.linear_model import LinearRegression
from sklearn.metrics import r2_score



## Select the starting date here
start = dt.datetime(2010,1,1)
end = dt.datetime.now()

## Select the ticker symbol here
ticker = 'AAPL'
df = web.DataReader(ticker, 'yahoo', start, end)

## Set the value of alpha for exponential smoothing ( between 0 and 1 )
alpha = 0.02

## Set the timeframe for moving average ( Days )
timeframe = 365

df.to_csv(ticker+'.csv')

df = pd.read_csv(ticker+'.csv', parse_dates = True)

df = df.rename_axis('index').reset_index()

x = df.iloc[:,0].values.reshape(-1,1)

y = df.iloc[:,4].values.reshape(-1,1)

## Linear Regression
x_train, x_test, y_train, y_test = train_test_split(x, y, test_size = 0.2, random_state = 0)

lr = LinearRegression()

lr.fit(x_train, y_train)

y_pred = lr.predict(x_test)

## Moving Average
rolling_y = (pd.DataFrame(y)).rolling(window=timeframe).mean()

## Exponential Smoothing
smoothing = [y[0]]
for n in range(1, len(y)):
    smoothing.append(alpha * y[n] + (1 - alpha) * smoothing[n-1])


print("Linear Regression")

print("\nCoefficient: ", lr.coef_)

print("\nIntercept: ", lr.intercept_)

print("\nR square: ", r2_score(y_test, y_pred))

style.use('ggplot')
plt.figure(figsize=(17, 8))
plt.plot(y, label="Stock Price ( {} )".format(ticker), color = "yellow")
plt.plot(x_train, lr.predict(x_train), label="Linear Regression", color = "blue")
plt.plot(rolling_y, label="Moving Average ( {} days )".format(timeframe), color = "red")
plt.plot(smoothing, label="Exponential Smoothing ( Alpha = {} )".format(alpha), color = "green")
plt.title("Stock Price vs Time ( {} )".format(ticker))
plt.xlabel("Time ( Days since "+str(start.date())+" )")
plt.ylabel("Stock Price ( $ / share )")
plt.legend(loc="upper left")
plt.show()
