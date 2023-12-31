// apis - back end와 연결할 AXIOS 함수
// assets- 이미지, 비디오 폰트
// components - 최소단위 컴포넌트들(버튼, 카드, 인풋)
// constans - 상수들
// interfaces - Type으로 사용할 인스터스들 저장
// sotres - 스토어 함수들 저장
// utils - 각종 메소드들 저장

import axios from "axios";
import { error } from "console";

// views - 큰뷰 또는 큰 페이지 (로그인,메인) 레이아웃 단위의
export const loginApi = async(data:any) => {

    const response = await axios
                            .get(`http://localhost:8080/user/login/`+data.userId+`/`+data.userPw)
                            .catch((error) => null);

    if(!response) return null;

    const result = response.data;
    return result;
}


export const signUpApi = async (data:any) => {
    const response = await axios
                            .post("http://localhost:8080/user/join",data)
                            .catch((error) => null);
    if(!response) return null;

    const result = response.data;
    return result;
}