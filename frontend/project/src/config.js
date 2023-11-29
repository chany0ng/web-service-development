// 로딩 중에, loading이라는 state변수 하나 만들어놓고 로딩상태아이콘 띄우기(212 강의)

const BASE_URL = 'http://localhost:8080/';
const ACCESS_URL = 'http://localhost:8080/api/check';
const REFRESH_URL = 'http://localhost:8080/api/token';
// const [isLoading, setIsLoading] = useState(false); // 로딩중 유무 state
// const [error, setError] = useState(null); // state로 에러 메시지 관리

export const getData = async (url) => {
  try {
    const accessToken = localStorage.getItem('accessToken');
    const response = await fetch(`${BASE_URL}${url}`, {
      method: 'GET',
      headers: {
        'Content-Type': 'application/json',
        Authorization: `Bearer ${accessToken}`
      }
    });
    let newStatus = null;
    if (response.status === 401) {
      newStatus = await sendAccessToken(accessToken);
      if (newStatus === 401) {
        return { status: 401, data: null };
      }
    }
    if (response.status === 200 || newStatus === 200) {
      const data = await response.json();
      console.log(`GET 데이터: ${JSON.stringify(data)}`);
      console.log(`GET status: ${response.status}`);
      return { status: 200, data }; //
    }
    throw new Error('GET요청 데이터를 가져오는데 실패했습니다.');
  } catch (error) {
    console.error(error);
    alert(error);
  }
};

// return 403: 권한 없음 -> /user/main
// return 401: refresh도종료(로그아웃) -> '/'
// return 200: 정상동작 -> data이용 가능
export const postData = async (url, body) => {
  try {
    const accessToken = localStorage.getItem('accessToken');
    let response = null;
    if (!accessToken) {
      response = await fetch(`${BASE_URL}${url}`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify(body)
      });
    } else {
      response = await fetch(`${BASE_URL}${url}`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
          Authorization: `${accessToken}`
        },
        body: JSON.stringify(body)
      });
    }
    if (response.status === 403) {
      return { status: 403, data: null };
    }
    let newStatus = null;
    if (response.status === 401) {
      newStatus = await sendAccessToken(accessToken);
      if (newStatus === 401) {
        return { status: 401, data: null };
      }
    }
    if (response.status === 200 || newStatus === 200) {
      const data = await response.json();
      console.log(`POST 데이터: ${JSON.stringify(data)}`);
      console.log(`POST status: ${response.status}`);
      return { status: 200, data }; //
    }
    throw new Error('POST요청 데이터를 가져오는데 실패했습니다.');
  } catch (error) {
    console.error(error);
    // alert(error);
  }
};

const updateRefreshToken = async () => {
  const refreshToken = localStorage.getItem('refreshToken');
  const response = await fetch(`${REFRESH_URL}`, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json'
    },
    body: JSON.stringify(refreshToken)
  });
  if (response.status === 401) {
    // refresh까지 만료 -> 로그아웃된 상태임 -> login
    return { status: 401 };
  }
  if (response === 200) {
    const data = await response.json();
    localStorage.setItem('accessToken', data.accessToken);
    return { status: 200 };
  }
  throw new Error('refresh토큰 갱신요청 에러발생');
};

// 토큰이 있을 때 실행하는 함수
// return 1-> status: 401 로그아웃 상태
// return 2-> status: 200 액세스 정상 상태
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
    } else if (response.status === 200) {
      return { status: 200 };
    } else {
      throw new Error('Access token 에러 발생');
    }
  } catch (error) {
    console.error(error);
    alert(error);
  }
};
