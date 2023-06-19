import React, { useState } from 'react'

import Card from '@mui/material/Card';
import CardActions from '@mui/material/CardActions';
import CardContent from '@mui/material/CardContent';
import Box from '@mui/material/Box';
import TextField from '@mui/material/TextField';
import Button from '@mui/material/Button';
import axios from 'axios';
import { useCookies } from 'react-cookie';

export default function Login() {
    // 유저 아이디, 비밀번호, 이름 state 생성
    const [userId, setUserId] = useState<string>('');
    const [userPw, setUserPw] = useState<string>('');
    
    const [cookies, setCookies] = useCookies();

    const [requestResult, setRequestResult] = useState<String>('');
    const loginHandler = () => {
        const data = {
            userId,
            userPw
        }
        axios
            .get(`http://localhost:8080/user/login/`+userId+`/`+userPw)
            .then((response) => {
                setRequestResult(response.data);
                console.log(requestResult);
                let userNm:String = response.data;
                setCookies('userNm', userNm);
                alert(cookies.userNm);
             })
             .catch((error) => {
                setRequestResult('Failed!!');
                console.log(requestResult);
             })
    }

  return (
    <Card sx={{ minWidth: 275, maxWidth : "50vw"}}>
      <CardContent>
          <Box>
            <TextField fullWidth label="아이디" variant="standard" onChange={(e) => setUserId(e.target.value)} />
            <TextField fullWidth label="비밀번호" type='password' variant="standard" onChange={(e) => setUserPw(e.target.value)} />
          </Box>
      </CardContent>
      <CardActions>
        <Button fullWidth onClick={() => {loginHandler()}} variant="contained">로그인</Button>
      </CardActions>
    </Card>
  )
}
