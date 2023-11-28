import { useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { sendAccessToken } from './config';

export const LoginPageAuthCheck = async () => {
  const navigation = useNavigate();
  const accessToken = localStorage.getItem('accessToken');
  const checkAccessToken = async () => {
    if (accessToken) {
      const { status } = await sendAccessToken(accessToken);
      if (status === 401) {
        navigation('/');
      }
      if (status === 200) {
        navigation('/user/main');
      }
    }
  };
  useEffect(() => {
    checkAccessToken();
  }, []); // 의존성 배열 비움

  return null;
};

export const MainPageAuthCheck = () => {
  const navigation = useNavigate();
  const accessToken = localStorage.getItem('accessToken');
  const checkAccessToken = async () => {
    if (accessToken) {
      const { status } = await sendAccessToken(accessToken);
      if (status === 401) {
        navigation('/');
      }
    } else {
      alert('로그인이 필요합니다!');
      navigation('/');
    }
  };
  useEffect(() => {
    checkAccessToken();
  }, []); // 의존성 배열 비움

  return null;
};
