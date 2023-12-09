// 로딩 중에, loading이라는 state변수 하나 만들어놓고 로딩상태아이콘 띄우기(212 강의)

const BASE_URL = 'http://localhost:8080/';
const ACCESS_URL = 'http://localhost:8080/api/check';
const REFRESH_URL = 'http://localhost:8080/api/token';
export const getFetch = async (url) => {
  try {
    const accessToken = localStorage.getItem('accessToken');
    let response = null;

    if (accessToken) {
      response = await fetch(`${BASE_URL}${url}`, {
        method: 'GET',
        headers: {
          'Content-Type': 'application/json',
          Authorization: `Bearer ${accessToken}`
        }
      });

      if (response.status === 401) {
        response = await updateRefreshToken();
        // 401만 반환 -> '/' 리다이렉트
      }
      return response;
    } else {
      response = await fetch(`${BASE_URL}${url}`, {
        method: 'GET',
        headers: {
          'Content-Type': 'application/json'
        }
      });

      return response;
    }
  } catch (error) {
    throw error;
  }
};

export const postFetch = async (url, body) => {
  try {
    const accessToken = localStorage.getItem('accessToken');
    let response = null;
    if (accessToken) {
      response = await fetch(`${BASE_URL}${url}`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
          Authorization: `Bearer ${accessToken}`
        },
        body: JSON.stringify(body)
      });
      // const data = await response.json();
      if (response.status === 401) {
        response = await updateRefreshToken();
        // 401만 반환 -> '/' 리다이렉트
        return response;
      }
      return response;
    }
    // else: 로그인 관련 페이지에서만 적용
    else {
      response = await fetch(`${BASE_URL}${url}`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify(body)
      });
      return response;
    }
  } catch (error) {
    throw error;
  }
};

const updateRefreshToken = async () => {
  try {
    const refreshToken = localStorage.getItem('refreshToken');
    const response = await fetch(`${REFRESH_URL}`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(refreshToken)
    });
    if (response.status === 200) {
      const data = await response.json();
      localStorage.setItem('accessToken', data.accessToken);
      alert('로그인이 갱신되었습니다!');
      window.location.reload();
    } else if (response.status === 401) {
      localStorage.removeItem('accessToken');
      localStorage.removeItem('refreshToken');
      return response;
    } else {
      throw new Error(`Failed to refresh login. Status: ${response.status}`);
    }
  } catch (error) {
    throw error;
  }
};

export const sendAccessToken = async (accessToken) => {
  try {
    const response = await fetch(ACCESS_URL, {
      method: 'GET',
      headers: {
        'Content-Type': 'application/json',
        Authorization: `Bearer ${accessToken}`
      }
    });
    if (response.status === 401) {
      return await updateRefreshToken();
    }
    return response;
  } catch (error) {
    throw error;
  }
};
