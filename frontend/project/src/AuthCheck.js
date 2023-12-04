import { sendAccessToken } from './config';

export const loginPageAuthCheck = async (navigation) => {
  try {
    const accessToken = localStorage.getItem('accessToken');
    if (accessToken) {
      const { status } = await sendAccessToken(accessToken);
      if (status === 401) {
        alert('로그인 만료!');
        navigation('/');
      }
      if (status === 200) {
        alert('이미 로그인 상태입니다!');
        navigation('/user/main');
      }
    }
    alert('access token없는 접속!');
  } catch (error) {
    console.error(error);
    alert(error);
  }
};

export const mainPageAuthCheck = async (navigation) => {
  try {
    const accessToken = localStorage.getItem('accessToken');
    if (accessToken) {
      const { status } = await sendAccessToken(accessToken);
      if (status === 401) {
        alert('로그인 만료!');
        navigation('/');
      } else if (status === 403) {
        alert('권한이 없습니다!');
        navigation('/user/main');
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
