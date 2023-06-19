import React, { useState } from 'react'
import axios from 'axios';

import Button from '@mui/material/Button';
import Card from '@mui/material/Card';
import CardActions from '@mui/material/CardActions';
import CardContent from '@mui/material/CardContent';
import Box from '@mui/material/Box';
import TextField from '@mui/material/TextField';
export default function SignUp() {
    // 유저 아이디, 비밀번호, 이름 state 생성
    const [userId, setUserId] = useState<string>('');
    const [userPw, setUserPw] = useState<string>('');
    const [userNm, setUserNm] = useState<string>('');


    const [requestResult, setRequestResult] = useState<String>('');
    const signUpHandler = () => {
        const data = {
            userId,
            userPw,
            userNm
        }
        axios
            .post("http://localhost:8080/user/join", data)
            .then((response) => {
                setRequestResult('Success!!');
             })
             .catch((error) => {
                setRequestResult('Failed!!');
             })
    }

  return (
    <Card sx={{ minWidth: 275, maxWidth : "50vw"}}>
      <CardContent>
          <Box>
            <TextField fullWidth label="아이디" variant="standard" onChange={(e) => setUserId(e.target.value)} />
            <TextField fullWidth label="비밀번호" type='password' variant="standard" onChange={(e) => setUserPw(e.target.value)} />
            <TextField fullWidth label="이름" variant="standard" onChange={(e) => setUserNm(e.target.value)} />
          </Box>
      </CardContent>
      <CardActions>
        <Button fullWidth onClick={() => {signUpHandler()}} variant="contained">회원가입</Button>
      </CardActions>
    </Card>
    
  )
}
