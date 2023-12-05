import { atom } from 'recoil';

export const userInfo = atom({
  key: 'userInfo',
  default: {
    id: null,
    pw: null
    // 등등 디폴트 값을 추가할 수 있음
  }
});
