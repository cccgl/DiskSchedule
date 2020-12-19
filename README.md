# DiskSchedule
# DiskSchedule
本项目是操作系统课程设计实验课  
题目如下：  
基于磁头引臂调度算法的磁盘I/O访问的调度及其时间参数计算的模拟实现  
功能要求  
(1)设计调度算法选择（界面）算法包括：  
先到先服务（FCFS）算法；  
最短查找时间优先（SSTF）算法：  
扫描算法（SCAN）；  
电梯算法（LOOK）。  
(2)设计磁盘参数设置（界面）：  
跨越1个磁道所用时间（单位：毫秒）、启动时间（单位：毫秒）；  
磁盘转速r（单位：转/分钟）；  
每磁道扇区（块）数、每扇区（块）字节数；  
每个盘面的磁道数（由外向内）固定为：0,1,2，……，198,199；  
随机产生算法中当前磁头所在磁道以及磁头移动方向；  
(3)实现多线程或多进程对磁盘的动态访问，实现进程或线程在访问磁盘时的被唤醒和等待的功能，并显示磁道I/O访问序列S；  
(4)基于上述情况，针对S：  
显示采用所选择的调度算法产生的相应的引臂移动序列；  
显示并计算引臂移动量和寻道时间；  
显示并计算平均旋转延迟时间；  
显示并计算传输时间；  
显示并计算所有访问处理时间   
实现  
（1）可用随机函数产生当前磁头所在磁道、磁头移动方向及S；  
（3）除FCFS算法外，对其它调度算法，可参考如下数据结构  
           char track[200]={0,0，……，0}；    //track[i]=m，表示磁道i有m个I/O请求；  

本人实现内容：ostest.java  
负责四个磁盘调度算法的代码实现  
先来先服务FCFS（first come first service)
![image](https://github.com/cccgl/DiskSchedule/raw/master/img/fcfs.png)

最短优先SSTF
![image](https://github.com/cccgl/DiskSchedule/raw/master/img/sstf.png)  

电梯算法SCAN
![image](https://github.com/cccgl/DiskSchedule/raw/master/img/scan.png)  

单向电梯算法C-SCAN
![image](https://github.com/cccgl/DiskSchedule/raw/master/img/cscan.png)  
