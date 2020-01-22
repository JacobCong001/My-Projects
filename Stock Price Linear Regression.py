import datetime as dt
import matplotlib.pyplot as plt
from matplotlib import style
import pandas as pd
import pandas_datareader.data as web
from sklearn.model_selection import train_test_split
from sklearn.linear_model import LinearRegression
from sklearn.metrics import r2_score

style.use('ggplot')

start = dt.datetime(2005,1,1)
end = dt.datetime(2020,1,12)

df = web.DataReader('AAPL', 'yahoo', start, end)

df.to_csv('aapl.csv')

df = pd.read_csv('aapl.csv', parse_dates = True)


df = df.rename_axis('index').reset_index()

x = df.iloc[:,0].values.reshape(-1,1)

y = df.iloc[:,4].values.reshape(-1,1)


x_train, x_test, y_train, y_test = train_test_split(x, y, test_size = 0.2, random_state = 0)

lr = LinearRegression()

lr.fit(x_train, y_train)

y_pred = lr.predict(x_test)


print(df)

print('Coefficient:', lr.coef_)

print('Intercept:', lr.intercept_)

print('R square:', r2_score(y_test, y_pred))

plt.scatter(x_test, y_test, color = "red")
plt.plot(x_train, lr.predict(x_train), color = "green")
plt.title("Stock Price vs Time")
plt.xlabel("Time")
plt.ylabel("Stock Price")
plt.show()
