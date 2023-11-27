import styles from './UserPayCharge.module.scss';
import TabBar from '../../../components/TabBar/TabBar';
import InnerTabBar from '../../../components/TabBar/InnerTabBar';
import Layout from '../../../layouts/Layout';
import Article from '../../../layouts/Article';
import { Card, Button, Container } from '@mui/material';

const outerTitle = ['회원정보 관리', '결제 관리', '이용정보 관리'];
const innerTitle = ['카드 금액 충전', '추가요금 결제'];
const outerTab = 'pay';
const innerTab = 'charge';
const url = {
  charge: '/user/pay/charge',
  'extra-charge': '/user/pay/extra-charge'
};

const UserPayCharge = () => {
  //todo: useEffct-> 보유금액 가져오기
  //todo: 금액 충전 버튼 클릭 기능 추가
  const existCash = 15000;
  return (
    <Layout>
      <TabBar title={outerTitle} select={outerTab} />
      <InnerTabBar title={innerTitle} select={innerTab} url={url} />
      <Article>
        <Container maxWidth="sm">
          <Card
            variant="outlined"
            sx={{
              borderColor: 'primary.main',
              padding: '0px',
              borderRadius: '5px'
            }}
            className={styles.card}
          >
            <h2>금액 사전 충전 시스템</h2>
            <div className={styles.favorite}>
              <div className={styles.notice}>
                <h3 style={{ textAlign: 'left' }}>
                  * 충전은 1,000원 단위로 가능합니다
                </h3>
                <h3>
                  * 충전한 금액으로 따릉이 대여, 추가요금 결제가 가능합니다
                </h3>
                <div className={styles.money}>
                  <h3 style={{ marginRight: '10px' }}>현재 보유 금액 : </h3>
                  <h1 className={styles.amount}>
                    {existCash.toLocaleString()}원
                  </h1>
                </div>
              </div>
              <div className={styles.inputBox}>
                <input
                  type="text"
                  name="cash"
                  id="cash"
                  // value={cash}
                  // onChange={inputStationHandler}
                  placeholder="충전 할 금액 입력"
                />
                <Button
                  variant="contained"
                  color="primary"
                  sx={{ fontSize: '1rem' }}
                  // onClick={addStationHandler}
                >
                  금액 충전
                </Button>
              </div>
            </div>
          </Card>
        </Container>
      </Article>
    </Layout>
  );
};

export default UserPayCharge;
