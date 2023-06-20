import React, { useState } from 'react'
import SignUp from './signUp'
import Login from './login'
import Box from '@mui/material/Box'

export default function Athentication() {
  // authView : true - 가입 / false -로그인
  const [authView, setAuthView ] = useState<boolean>(true);
  return (
    <>
    <Box display='flex' height='100vh'>
        <Box flex ={1} display='flex' justifyContent='center' alignItems='center'>
        </Box>
        <Box flex ={1} display='flex' justifyContent='center' alignItems='center'>
          {authView  ?(<SignUp setAuthView={setAuthView} />) : (<Login setAuthView={setAuthView}/>)}
          
        </Box>
    </Box>
    </>
  )
}
