import React, { useState } from 'react'
import axios from 'axios';

import Button from '@mui/material/Button';
import Card from '@mui/material/Card';
import CardActions from '@mui/material/CardActions';
import CardContent from '@mui/material/CardContent';
import Box from '@mui/material/Box';
import TextField from '@mui/material/TextField';
import Typography from '@mui/material/Typography';
import { loginApi, signUpApi } from '../../../apis';

interface Props {
  setAuthView :(authView : boolean) => void,
}

export default function SignUp(props : Props) {
    // 유저 아이디, 비밀번호, 이름 state 생성
    const [userId, setUserId] = useState<string>('');
    const [userPw, setUserPw] = useState<string>('');
    const [userNm, setUserNm] = useState<string>('');
    const [requestResult, setRequestResult] = useState<String>('');

    const { setAuthView } = props

    const signUpHandler = async () => {
        const data = {
            userId,
            userPw,
            userNm
        }
        const signUpResponse = await signUpApi(data);

        if(signUpResponse) {alert("회원가입에 실패했습니다."); return;}
    }

  return (
    <Card sx={{ minWidth: 275, maxWidth : "50vw", padding: 5}}>
      <Box>
        <Typography variant='h5'>회원가입</Typography>
      </Box>
          <Box height={'50vh'}>
            <TextField fullWidth label="아이디" variant="standard" onChange={(e) => setUserId(e.target.value)} />
            <TextField fullWidth label="비밀번호" type='password' variant="standard" onChange={(e) => setUserPw(e.target.value)} />
            <TextField fullWidth label="이름" variant="standard" onChange={(e) => setUserNm(e.target.value)} />
          </Box>
        <Box component='div'>
          <Button fullWidth onClick={() => {signUpHandler()}} variant="contained">
            회원가입
          </Button> 
        </Box>
        <Box component='div' display='flex' mt={2}>
          <Typography>이미 계정이 있으신가요?</Typography>
          <Typography fontWeight={800} ml={1} onClick = {() => setAuthView(false)} >로그인</Typography>
        </Box>
    </Card>
    
  )
}
