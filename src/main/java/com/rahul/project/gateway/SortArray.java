package com.rahul.project.gateway;

public class SortArray {

    public static void main(String[] args) {
        int[] arr = new int[]{-5,-7,1,6,-3,8};
            int size = arr.length;
            for(int i = 0; i < size; i++){
                if(arr[i] < 0 && arr[i+1] < 0){
                    if(arr[i] < arr[i+1]) {
                        int temp = arr[i];
                        arr[i + 1] = arr[i];
                        arr[i] = temp;
                    }
                }else if(arr[i] > 0 && arr[i+1] >0){
                    if (arr[i] > arr[i+1]) {
                        int temp = arr[i];
                        arr[i + 1] = arr[i];
                        arr[i] = temp;
                    }
                }

        }
    }
}
