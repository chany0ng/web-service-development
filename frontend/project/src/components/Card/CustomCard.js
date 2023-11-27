import styles from './CustomCard.module.scss';
import { Container, Card, Button } from '@mui/material';
import { useNavigate } from 'react-router-dom';

const CustomCard = (props) => {
  const navigate = useNavigate();
  const info = {
    name: '박찬용',
    moeny: 15000,
    bicycleNumber: 123456,
    leftTime: 52
  };

  return (
    <Container maxWidth="xs">
      <Card
        variant="outlined"
        sx={{
          borderColor: 'primary.main',
          padding: '0px',
          borderRadius: '5px'
        }}
        className={styles.card}
      >
        <div className={styles.cardHeader}>
          <span>
            <h1>{info.name}</h1> 회원님
          </span>
          <Button
            onClick={() => {
              navigate('/user/info/edit');
            }}
            variant="contained"
            color="primary"
            sx={{ fontSize: '1rem' }}
          >
            내 정보 관리 {`>`}
          </Button>
        </div>
        <div className={styles.cardBody}>
          <div className={styles.cardMoney}>
            <div>
              현재 보유중인 금액 <h3>{info.moeny}원</h3>
            </div>
            <Button
              onClick={() => {
                navigate('/user/pay/charge');
              }}
              variant="contained"
              color="primary"
              sx={{ fontSize: '1rem', marginLeft: '2rem' }}
            >
              충전하기 {`>`}
            </Button>
          </div>
          <div className={styles.cardInfo}>현재 대여중인 따릉이 정보</div>
        </div>
      </Card>
    </Container>
  );
};

export default CustomCard;
