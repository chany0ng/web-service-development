import { atom } from 'recoil';
import { recoilPersist } from 'recoil-persist';

const { persistAtom } = recoilPersist({
  key: 'sessionStorage', // 고유한 key 값
  storage: sessionStorage
});
export const userInfo = atom({
  key: 'userInfo',
  default: {
    user_id: null,
    bike_id: null,
    cash: 0,
    hour: 0,
    overfee: 0,
    email: null,
    phone_number: null,
    isRented: 0
    // 등등 디폴트 값을 추가할 수 있음
  },
  effects_UNSTABLE: [persistAtom]
});
export const adminInfo = atom({
  key: 'adminInfo',
  default: {
    user_id: null,
    report: 0
    // 등등 디폴트 값을 추가할 수 있음
  },
  effects_UNSTABLE: [persistAtom]
});
