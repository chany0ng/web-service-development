// API는 config.js 파일에 일괄적으로 관리하면서 import, export 통해 사용하면 좋습니다.
// 백엔드 서버 IP 가 변경되면 fetch 함수를 일일이 찾아서 수정해주지 않아도 됩니다.
// const BASE_URL = 'http://10.58.5.151:8000'
// export const GET_PRODUCT_API = `${BASE_URL}/products`
// 로딩 중에, loading이라는 state변수 하나 만들어놓고 로딩상태아이콘 띄우기(212 강의)

const [isLoading, setIsLoading] = useState(false); // 로딩중 유무 state
const [error, setError] = useState(null); // state로 에러 메시지 관리
// 각 컴포넌트에서 위 처럼 설정하면 된다.
//! API요청은 useEffect로 설정 -> 페이지 렌더링될 때 마다 데이터 불러오기
const fetchMoviesHandler = async () => {
  try {
    setIsLoading(true);
    const response = await fetch('http://swapi.dev/api/films/');
    if (!response.ok) {
      // response.status 도 있으니 체크
      throw new Error('Something wrong');
    }
    const data = response.json(); // json -> js로 변환

    const transformedMovies = data.results.map((movieData) => {
      return {
        // 여러 key:vallue 중에, 필요한 것만 뽑아서 객체로 반환
        id: movieData.episode_id,
        title: movieData.title,
        openingText: movieData.opening_crawl
      };
    });
    return data; // API요청 템플릿을 만들어 놓은 것
  } catch (error) {
    setError(error.message);
  } finally {
    setIsLoading(false);
  }
};

//! POST요청 하기, 보낼 때 json으로 변환해서 보낸다
const addMovieHandler = async (movie) => {
  const response = await fetch('https:// ~어쩌구', {
    method: 'POST',
    body: JSON.stringify(movie),
    headers: { 'Content-Type': 'application.json' }
  });
  const data = response.json();
};
