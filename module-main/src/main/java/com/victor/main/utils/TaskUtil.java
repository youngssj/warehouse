package com.victor.main.utils;

import java.util.List;

import io.reactivex.Observable;

/**********************************
 * @author peter
 * @date 2022/5/25
 **********************************/
public class TaskUtil {
    // 根据list生成一个串行的请求
    public static <T,F> Observable<F> createRequestByList(List<T> list,TaskListener<T,F> taskListener) {
          if(taskListener == null){
                return  null;
          }
          Observable<F> response = null;
          T item = null;
          for (int i = 0; i < list.size(); i++) {
                item = list.get(i);
                if (item != null) {
                      Observable<F> itemResponse = taskListener.createRequest(item);

                      if (response == null) {
                            response = itemResponse;
                      } else {
                            response = response.concatWith(itemResponse);
                      }
                }
          }
          return  response;
    }
      public interface TaskListener<T,F>{
            Observable<F> createRequest(T t);
      }
}

