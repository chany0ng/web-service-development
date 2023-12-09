import { atom } from 'recoil';
import { recoilPersist } from 'recoil-persist';

const { persistAtom } = recoilPersist();
export const userInfo = atom({
  key: 'userInfo',
  default: {
    user_id: null,
    bike_id: null,
    cash: 0,
    hour: 0,
    email: null,
    phone_number: null,
    rented: false
    // 등등 디폴트 값을 추가할 수 있음
  },
  effects_UNSTABLE: [persistAtom]
});
