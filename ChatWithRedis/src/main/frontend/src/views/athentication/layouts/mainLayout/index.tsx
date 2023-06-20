import React, { useEffect, useState } from 'react'
import Navigation from '../../navigation'
import Athentication from '../..'
import ChatMain from '../../../chatMain'
import { useUserStore } from '../../../../stores'
import { useCookies } from 'react-cookie'
import axios from 'axios'

export default function MaxinLayout() {

  const {user} = useUserStore();
  const [cookies] = useCookies();
  const [chatListResponse, setChatListResponse] = useState<string>('');

  const getChatList = async () => {
    await axios.get("http://loccalhost/chat/list").then((response) => {
      setChatListResponse(response.data);
    }).catch((error) => '');

  }
  
  useEffect(() => {
    const userNm =  cookies.userNm;
    if(userNm) getChatList();
  }, [user]);

  return (
    <>
        <Navigation />

        {chatListResponse ? (<ChatMain />) : ( <Athentication />)}
    </>
  )
}
