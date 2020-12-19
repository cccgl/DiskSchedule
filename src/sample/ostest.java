package sample;

import java.util.*;
import java.util.Random;
import java.lang.Math;

public class ostest {
    //**输入**
    public int tCiDao, tStart;//跨越磁道时间tCiDao    启动时间tStart
    public int rRound;//转速
    public int nShanQu, nByte;//每磁道的扇区数 每扇区中字节数
    public int ranCiDao, ranFangXiang;//当前随机的磁道   随机的方向
    public int number = 10;//要求访问序列的个数
    public int readNum = 1000;//每次读取字节数
    public int[] track = new int[number];//请求访问磁道序列
    public int[] RequestTime = new int[number];//磁道请求访问的时间
    public int[] Threadnumber = new int[number];//提出请求的线程

    //**输出**
    public int sum = 0;//磁盘移动总和
    public int tFind = 0;//寻道时间
    public int tAvgRound = 0;//平均旋转延迟时间
    public int tComeIn = 0;//传输时间
    public int[] trackOut = new int[number];//运用算法后的输出序列

    public void createtrack() {
        for (int i = 0; i < 10; i++) {
            Threadnumber[i] = i;
            Random ran2 = new Random();
            track[i] = ran2.nextInt(200);//当前磁道随机数、
        }
        for (int i = 0; i < 10; i++) {
            Random ran2 = new Random();
            RequestTime[i] = ran2.nextInt(20) + i * 20;//当前磁道随机数、
        }
    }

    public ostest() {//构造函数 创建track和time数组
        createtrack();
    }

    ;

    //先来先服务
    void FCFS() {
        for (int i = 0; i < number; i++) {
            trackOut[i] = track[i];
            if (i == 0)
                sum = Math.abs(ranCiDao - track[0]);
            else
                sum += Math.abs(track[i - 1] - track[i]);
        }
        tFind = tStart + tCiDao * sum;
        tAvgRound = (60000 / 2) / rRound;//1min=60000ms 平均需除以2
        tComeIn = readNum / (rRound * nShanQu * nByte);
    }

    ;

    //最短优先
    void SSTF() {
        int[] tracksort;
        tracksort = Arrays.copyOf(track, number);
        Arrays.sort(tracksort);

        int i = 0;
        int choice = 0;
        if (tracksort[0] > ranCiDao)//所有访问磁道都大于当前磁道 向前输出
        {
            sum = tracksort[0] - ranCiDao;
            for (int k = 0; k < number; k++) {
                //System.out.print(track[k] + "    ");//磁盘移动序列
                trackOut = tracksort;
            }
            for (int k = 0; k < number - 1; k++) {
                sum += (tracksort[k + 1] - tracksort[k]);
            }
            choice = 1;
        } else if (tracksort[number - 1] < ranCiDao)//所有访问磁道都小于当前磁道 向后输出
        {
            sum = ranCiDao - tracksort[number - 1];
       //     System.out.println("磁道访问序列为：");
            for (int k = 0; k < number; k++) {
                trackOut[k] = track[number - 1 - k];
            }
            for (int k = number - 1; k >= 1; k--) {
                sum += (tracksort[k] - tracksort[k - 1]);
            }
            choice = 2;
        }
        for (i = 0; i < 10; i++) {
            if (tracksort[i] >= ranCiDao)//找到
            {
                choice = 3;
                break;
            }
        }
        switch (choice) {
            case 1://todo 还能不能出来case1情况
            case 2: {
                tFind = tStart + tCiDao * sum;
                tAvgRound = (60000 / 2) / rRound;//1min=60000ms 平均需除以2
                tComeIn = readNum / (rRound * nShanQu * nByte);
                break;
            }
            case 3: {
                int CurrentCidao = ranCiDao;
                int low = i - 1;//反向双指针
                int high = i;
                int loc = 0;
                //TODO
                while ((low >= 0) & (high < number)) {
                    if (Math.abs(CurrentCidao - tracksort[low]) > Math.abs(CurrentCidao - tracksort[high])) {
                        trackOut[loc] = tracksort[high];
                        sum += Math.abs(CurrentCidao - tracksort[high]);
                        CurrentCidao = tracksort[high];
                        high += 1;

                    } else if (Math.abs(CurrentCidao - tracksort[low]) < Math.abs(CurrentCidao - tracksort[high])) {
                        trackOut[loc] = tracksort[low];
                        sum += Math.abs(CurrentCidao - tracksort[low]);
                        CurrentCidao = tracksort[low];
                        low -= 1;

                    } else if (Math.abs(CurrentCidao - tracksort[low]) == Math.abs(CurrentCidao - tracksort[high])) {
                        trackOut[loc] = tracksort[high];
                        sum += Math.abs(CurrentCidao - tracksort[high]);
                        CurrentCidao = tracksort[high];
                        high += 1;
                    }
                    loc++;
                }
                while (low >= 0) {
                    trackOut[loc] = tracksort[low];
                    sum += Math.abs(CurrentCidao - tracksort[low]);
                    CurrentCidao = tracksort[low];
                    low -= 1;
                    loc++;
                }
                while (high < number) {
                    trackOut[loc] = tracksort[high];
                    sum += Math.abs(CurrentCidao - tracksort[high]);
                    CurrentCidao = tracksort[high];
                    high += 1;
                    loc++;
                }
                tFind = tStart + tCiDao * sum;
                tAvgRound = (60000 / 2) / rRound;//1min=60000ms 平均需除以2
                tComeIn = readNum / (rRound * nShanQu * nByte);
                break;
            }
        }
    }

    ;

    //电梯算法
    void SCAN() {
        int[] tracksort;
        tracksort = Arrays.copyOf(track, number);
        Arrays.sort(tracksort);
        int CurCiDao = ranCiDao;
        if (tracksort[0] > ranCiDao) {
            //0向内 1向外
            if (ranFangXiang == 1)//  e.g  当前磁道为10 请求访问都在10后 且方向向外 按升序输出
            {
                sum = Math.abs(CurCiDao - tracksort[0]);
                for (int i = 0; i < number; i++) {
                    trackOut = tracksort;
                }
                for (int k = 0; k < number - 1; k++) {
                    sum += Math.abs(tracksort[k + 1] - tracksort[k]);
                }
            } else if (ranFangXiang == 0)//SCAN算法由里向外改为由外向里 所以会反向输出
            {
                sum = Math.abs(CurCiDao - tracksort[number - 1]);
                for (int i = 0; i < number; i++)
                    trackOut[i] = tracksort[number - 1 - i];

                for (int k = number - 1; k >= 1; k--) {
                    sum += Math.abs(tracksort[k] - tracksort[k - 1]);
                }
            }
        } else if (tracksort[number - 1] < ranCiDao) {
            sum = Math.abs(CurCiDao - tracksort[number - 1]);
            for (int i = 0; i < number; i++)
                trackOut[i] = tracksort[number - 1 - i];
            for (int k = number - 1; k >= 1; k--)
                sum += Math.abs(tracksort[k] - tracksort[k - 1]);
        } else {                  //i求出第一个比当前磁道大的位置
            int i = 0;
            for (i = 0; i < number; i++) {
                if (tracksort[i] >= ranCiDao)
                    break;
            }
            int loc = 0;
            if (ranFangXiang == 1)//方向向外时先输出大于当前磁道的 然后反向输出剩下的
            {
                for (int j = i; j < number; j++, loc++)
                    trackOut[loc] = tracksort[j];
                for (int j = i - 1; j >= 0; j--, loc++)
                    trackOut[loc] = tracksort[j];
                sum = Math.abs(CurCiDao - tracksort[i]);
                for (int k = i; k < number - 1; k++)
                    sum += Math.abs(tracksort[k + 1] - tracksort[k]);

                //10 20 30 (35) 40 50 60 当到达60需要回到30
                sum += Math.abs(tracksort[number - 1] - tracksort[i - 1]);

                for (int k = i - 1; k > 0; k--)
                    sum += Math.abs(tracksort[k] - tracksort[k - 1]);
            } else if (ranFangXiang == 0)//方向向内时先反向输出前面小于当前磁道的 然后正向输出剩下的
            {
                for (int j = i - 1; j >= 0; j--, loc++)
                    trackOut[loc] = tracksort[j];
                for (int j = i; j < number; j++, loc++)
                    trackOut[loc] = tracksort[j];

                sum = Math.abs(CurCiDao - tracksort[i]);

                for (int k = i - 1; k > 0; k--)
                    sum += Math.abs(tracksort[k] - tracksort[k - 1]);

                //10 20 30 (35) 40 50 60 当到达10需要回到40
                sum += Math.abs(tracksort[0] - tracksort[i]);

                for (int k = i; k < number - 1; k++)
                    sum += Math.abs(tracksort[k + 1] - tracksort[k]);
            }
        }

        tFind = tStart + tCiDao * sum;

        tAvgRound = (60000 / 2) / rRound;

        tComeIn = readNum / (rRound * nShanQu * nByte);

    }

    ;

    //单向电梯算法
    void LOOK() {
        int[] tracksort;
        tracksort = Arrays.copyOf(track, number);
        Arrays.sort(tracksort);
        int CurCiDao = ranCiDao;
        int loc = 0;
        if (tracksort[0] > ranCiDao) {
            //  e.g  当前磁道为10 请求访问都在10后 且方向向外向里都按升序输出
            sum = Math.abs(CurCiDao - tracksort[0]);
            for (int i = 0; i < number; i++, loc++) {
                trackOut[loc] = tracksort[i];
            }
            for (int k = 0; k < number - 1; k++) {
                sum += Math.abs(tracksort[k + 1] - tracksort[k]);
            }
        } else if (tracksort[number - 1] < ranCiDao) {
            sum = Math.abs(CurCiDao - tracksort[number - 1]);
    //        System.out.println("磁道访问序列为：");
            for (int i = number - 1; i >= 0; i--, loc++)//对于此情况只会反向输出
                trackOut[loc] = tracksort[i];
            for (int k = number - 1; k >= 1; k--)
                sum += Math.abs(tracksort[k] - tracksort[k - 1]);
        } else {
            int i = 0;//i求出第一个比当前磁道大的位置
            for (i = 0; i < number; i++) {
                if (tracksort[i] >= ranCiDao)
                    break;
            }
            if (ranFangXiang == 1)//方向向外时先输出大于当前磁道的 然后方向重新回到第一位输出剩下的
            {
                for (int j = i; j < number; j++, loc++)
                    trackOut[loc] = tracksort[j];
                for (int j = 0; j < i; j++, loc++)
                    trackOut[loc] = tracksort[j];

                sum = Math.abs(CurCiDao - tracksort[i]);

                for (int k = i; k < number - 1; k++)
                    sum += Math.abs(tracksort[k + 1] - tracksort[k]);

                //10 20 30 (35) 40 50 60 当到达60需要回到10
                sum += Math.abs(tracksort[number - 1] - tracksort[0]);

                for (int k = 0; k < i; k++)
                    sum += Math.abs(tracksort[k + 1] - tracksort[k]);
            } else if (ranFangXiang == 0)//方向向内时先输出前面小于当前磁道的 然后回到最后一个向内输出剩下的
            {
                for (int j = i - 1; j >= 0; j--, loc++)
                    trackOut[loc] = tracksort[j];
                for (int j = number - 1; j >= i; j--, loc++)
                    trackOut[loc] = tracksort[j];
                sum = Math.abs(CurCiDao - tracksort[i]);
                for (int k = i - 1; k > 0; k--)
                    sum += Math.abs(tracksort[k] - tracksort[k - 1]);

                //10 20 30 (35) 40 50 60 当到达10需要回到60
                sum += Math.abs(tracksort[0] - tracksort[number - 1]);

                for (int k = number - 1; k > i; k--)
                    sum += Math.abs(tracksort[k] - tracksort[k - 1]);
            }
        }

        tFind = tStart + tCiDao * sum;

        tAvgRound = (60000 / 2) / rRound;//1min=60000ms 平均需除以2

        tComeIn = readNum / (rRound * nShanQu * nByte);

    }

    ;
}
