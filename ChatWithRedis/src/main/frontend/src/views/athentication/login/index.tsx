import React, { useState } from 'react'

import Card from '@mui/material/Card';
import CardActions from '@mui/material/CardActions';
import CardContent from '@mui/material/CardContent';
import Box from '@mui/material/Box';
import TextField from '@mui/material/TextField';
import Button from '@mui/material/Button';
import axios from 'axios';
import { useCookies } from 'react-cookie';
import { useUserStore } from '../../../stores';
import Typography from '@mui/material/Typography';
import CardHeader from '@mui/material/CardHeader';
import { loginApi } from '../../../apis';

interface Props {
  setAuthView :(authView : boolean) => void,
}

export default function Login(props : Props) {
    // 유저 아이디, 비밀번호, 이름 state 생성
    const [userId, setUserId] = useState<string>(''); // state 내부에서만사용
    const [userPw, setUserPw] = useState<string>('');
    const [requestResult, setRequestResult] = useState<String>('');
    const [cookies, setCookies] = useCookies();
    const {user, setUser} = useUserStore(); // store 외부에서 데이터 다룸
    
    const { setAuthView } = props


    const loginHandler = async () => {
        const data = {
            userId,
            userPw
        }

        const signInResponse = await loginApi(data);
        
        if(!signInResponse) alert("로그인에 실패했습니다.")

        setRequestResult(signInResponse);
        console.log(requestResult);
        let userNm:String = signInResponse;
        setCookies('userNm', userNm);
        setUser(userNm);
    }

  return (
    <Card sx={{ minWidth: 275, maxWidth : "50vw", padding: 5}}>
      <Box>
        <Typography variant='h5'>로그인</Typography>
      </Box>
          <Box height={'50vh'}>
            <TextField fullWidth label="아이디" variant="standard" onChange={(e) => setUserId(e.target.value)} />
            <TextField fullWidth label="비밀번호" type='password' variant="standard" onChange={(e) => setUserPw(e.target.value)} />
          </Box>
        <Box component='div'>
          <Button fullWidth onClick={() => {loginHandler()}} variant="contained">
            로그인
          </Button> 
        </Box>
        <Box component='div' display='flex' mt={2}>
          <Typography>신규 사용자 이신가요?</Typography>
          <Typography fontWeight={800} ml={1} onClick = {() => setAuthView(true)} >회원가입</Typography>
        </Box>
    </Card>
  )
}
