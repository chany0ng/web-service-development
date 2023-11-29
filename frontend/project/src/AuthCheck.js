import { sendAccessToken } from './config';

export const loginPageAuthCheck = async (navigation) => {
  const accessToken = localStorage.getItem('accessToken');
  if (accessToken) {
    const { status } = await sendAccessToken(accessToken);
    if (status === 401) {
      navigation('/');
    }
    if (status === 200) {
      navigation('/user/main');
    }
  }
  alert('access token없는 접속!');
};

export const mainPageAuthCheck = async (navigation) => {
  const accessToken = localStorage.getItem('accessToken');
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
