// API는 config.js 파일에 일괄적으로 관리하면서 import, export 통해 사용하면 좋습니다.
// 백엔드 서버 IP 가 변경되면 fetch 함수를 일일이 찾아서 수정해주지 않아도 됩니다.
// const BASE_URL = 'http://10.58.5.151:8000'
// export const GET_PRODUCT_API = `${BASE_URL}/products`

// // 사용하는 컴포넌트
// import { GET_PRODUCT_API } from '../../../config.js';
// ...
// fetch(`${GET_PRODUCT_API}/5`).then(...).then(...);
