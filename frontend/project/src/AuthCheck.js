import { useEffect } from 'react';
import { useNavigate } from 'react-router-dom';

const AuthCheck = () => {
  const navigation = useNavigate();

  useEffect(() => {
    const token = localStorage.getItem('token'); // 로컬 스토리지에서 토큰을 가져옴
    console.log('Auth Check!');
    if (!token) {
      // 토큰이 없으면 로그인 페이지로 리다이렉트
      alert('로그인이 필요합니다!');
      navigation('/signin');
    }
  }, [navigation]);

  return null;
};

export default AuthCheck;
