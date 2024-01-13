package com.example.springsecurityusersroles;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.Arrays;

import static java.util.Arrays.stream;

@SpringBootApplication
public class SpringSecurityUsersRolesApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringSecurityUsersRolesApplication.class, args);





		// messive (1,2,5,3,6,7, 8) k = 8
// output - count combination for sum 8 , 5-3, 6-2, 7-1, 8
// output like this pattern {8=8, 1+7=8, 2+6=8, 5+3=8}

//		int[] massive = {1,2,5,3,6,7, 8};
//		int count = 0;
//		for( int i = 0; i < massive.length; i++){
//			if(massive[i] == 8 && massive.length == i+1){
//				count++;
//			}
//			for(int k = i+1; k < massive.length; k++) {
//				if(massive[i] + massive[k] == 8 ) {
//					count++;
//				}
//
//			}
//		}
//		long value = Arrays.stream(massive).filter(a -> a%2==0).count();
//
//		System.out.println(value);


	}


}




