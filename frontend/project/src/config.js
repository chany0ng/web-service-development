// API는 config.js 파일에 일괄적으로 관리하면서 import, export 통해 사용하면 좋습니다.
// 백엔드 서버 IP 가 변경되면 fetch 함수를 일일이 찾아서 수정해주지 않아도 됩니다.
// 로딩 중에, loading이라는 state변수 하나 만들어놓고 로딩상태아이콘 띄우기(212 강의)

const BASE_URL = 'http://localhost:8080/';
// const [isLoading, setIsLoading] = useState(false); // 로딩중 유무 state
// const [error, setError] = useState(null); // state로 에러 메시지 관리
export const getData = async (url) => {
  try {
    const response = await fetch(`${BASE_URL}${url}`);
    if (!response.ok) {
      throw new Error('API GET요청 에러 발생');
    }
    const data = response.json(); // json -> js로 변환
    return { status: response.status, data }; // API요청 템플릿을 만들어 놓은 것
  } catch (error) {
    console.error(error);
    throw error;
  }
};

export const postData = async (url, body) => {
  try {
    const response = await fetch(`${BASE_URL}${url}`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(body)
    });
    console.log('POST FETCH 성공!');
    if (!response.ok) {
      throw new Error('API POST요청 에러 발생');
    }
    const data = response.json();
    return { status: response.status, data };
  } catch (error) {
    throw error;
  }
};
