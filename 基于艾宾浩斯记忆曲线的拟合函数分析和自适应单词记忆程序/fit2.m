clear;
x=xlsread('D:\matlab\bin\paper\test.xlsx',1,'A1:W1');
y=xlsread('D:\matlab\bin\paper\test.xlsx',1,'A2:W2');
plot(x,y);
hold on;
myfunc=inline('beta(1)*exp(-beta(2)*x)+beta(3)+beta(4)-beta(5)*x.^(1/beta(6))+beta(7)*(x+0.01).^(-beta(8))','beta','x');
try
beta0=[20,0.01,20,1,1,1,1,1];
catch
end
beta=nlinfit(x,y,myfunc,beta0);
k=0:0.01:720;
t=beta(1)*exp(-beta(2)*k)+beta(3)+beta(4)-beta(5)*k.^(1/beta(6))+beta(7)*(k+0.01).^(-beta(8));
plot(k,t);
hold on;
s=ones(1,72001);
w=ones(1,144001);
for i=1:144001
    w(i)=1/i;
end
con1=conv(t,s).*w;
r=0:0.01:1440;
myfunc1=inline('beta1(1)*exp(-beta1(2)*r)+beta1(3)+beta1(4)-beta1(5)*r.^(1/beta1(6))+beta1(7)*(r+0.01).^(-beta1(8))','beta1','r');
beta1=nlinfit(r,con1,myfunc1,beta0);
plot(r,con1);
hold on;