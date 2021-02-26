clear;
x=xlsread('D:\matlab\bin\paper\test.xlsx',1,'A1:W1');
y=xlsread('D:\matlab\bin\paper\test.xlsx',1,'A2:W2');
plot(x,y);
hold on;
%猜测1：线性函数
myfunc1 = inline('beta(1)*x+beta(2)','beta','x');
beta0 = [0.01,100];
beta = nlinfit(x,y,myfunc1,beta0);
k=0:0.0001:720;
t=beta(1)*k+beta(2);
plot(k,t);
hold on;
clear beta0;
%反比例函数的幂函数
myfunc2 = inline('beta1(1)*(x+0.01).^beta1(2)','beta1','x');
beta0 = [10,-0.1];
beta1 = nlinfit(x,y,myfunc2,beta0);
t1=beta1(1)*k.^beta1(2);
plot(k,t1);
hold on;
clear beta0;
%指数函数
myfunc3=inline('beta2(1)*exp(-beta2(2)*x)','beta2','x');
beta0=[50,0.01];
beta2=nlinfit(x,y,myfunc3,beta0);
t2=beta2(1)*exp(-beta2(2)*k);
plot(k,t2);
hold on;
clear beta0
%有截距的指数函数
myfunc4=inline('beta3(1)*exp(-beta3(2)*x)+beta3(3)','beta3','x');
beta0=[50,0.01,50];
beta3=nlinfit(x,y,myfunc4,beta0);
t3=beta3(1)*exp(-beta3(2)*k)+beta3(3);
plot(k,t3);
hold on;
clear beta0;
%幂函数
myfunc5=inline('beta4(1)-beta4(2)*x.^(1/beta4(3))','beta4','x');
beta0=[1,1,1];
beta4=nlinfit(x,y,myfunc5,beta0);
t4=beta4(1)-beta4(2)*k.^(1/beta4(3));
plot(k,t4);
hold on;
clear beta0;