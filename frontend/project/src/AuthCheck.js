import { sendAccessToken } from './config';

export const loginPageAuthCheck = async (navigation) => {
  try {
    const accessToken = localStorage.getItem('accessToken');
    if (accessToken) {
      const response = await sendAccessToken(accessToken);
      if (response.status === 401) {
        alert('로그인 만료!');
        navigation('/');
      } else if (response.status === 200) {
        alert('이미 로그인 상태입니다!');
        navigation('/user/main');
      } else {
        throw new Error(`로그인 페이지 토큰인증 에러: ${response.status}`);
      }
    }
  } catch (error) {
    console.error(error);
    alert(error);
  }
};

export const mainPageAuthCheck = async (navigation) => {
  try {
    const accessToken = localStorage.getItem('accessToken');
    if (accessToken) {
      const response = await sendAccessToken(accessToken);
      if (response.status === 401) {
        alert('로그인 만료!');
        navigation('/');
      } else if (response.status === 403) {
        alert('권한이 없습니다!');
        navigation('/user/main');
      } else if (response.status !== 200) {
        throw new Error(`메인 페이지 토큰 인증 에러: ${response.status}`);
      }
    } else {
      alert('로그인이 필요합니다!');
      navigation('/');
    }
  } catch (error) {
    console.error(error);
    alert(error);
  }
};
