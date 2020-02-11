import datetime as dt
import matplotlib.pyplot as plt
from matplotlib import style
import pandas as pd
import pandas_datareader.data as web
from sklearn.model_selection import train_test_split
from sklearn.linear_model import LinearRegression
from sklearn.metrics import r2_score

style.use('ggplot')

## Select the starting date here
start = dt.datetime(2005,1,1)
end = dt.datetime.now()

## Select the ticker symbol here
ticker = 'AAPL'
df = web.DataReader(ticker, 'yahoo', start, end)

df.to_csv(ticker+'.csv')

df = pd.read_csv(ticker+'.csv', parse_dates = True)

df = df.rename_axis('index').reset_index()

x = df.iloc[:,0].values.reshape(-1,1)

y = df.iloc[:,4].values.reshape(-1,1)


x_train, x_test, y_train, y_test = train_test_split(x, y, test_size = 0.2, random_state = 0)

lr = LinearRegression()

lr.fit(x_train, y_train)

y_pred = lr.predict(x_test)

rolling_y = (pd.DataFrame(y)).rolling(window=365).mean()

print(df)

print()

print('Coefficient:', lr.coef_)

print('Intercept:', lr.intercept_)

print('R square:', r2_score(y_test, y_pred))

plt.plot(y, label='Daily Close Price', color = "yellow")
plt.plot(x_train, lr.predict(x_train), label='Price Regression Line', color = "red")
plt.plot(rolling_y, label='Moving Average ( 365 days )', color = "blue")
plt.title("Stock Price vs Time ("+ticker+")")
plt.xlabel("Time ( Days since "+str(start.date())+" )")
plt.ylabel("Stock Price ( $ / share )")
plt.legend(loc='upper left')
plt.show()
