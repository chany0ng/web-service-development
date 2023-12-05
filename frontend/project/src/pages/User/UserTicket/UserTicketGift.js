import InnerTabBar from '../../../components/TabBar/InnerTabBar';
import Article from '../../../layouts/Article';
import Layout from '../../../layouts/Layout';
import styles from './UserTicketGift.module.scss';
import { Card, Button, Container } from '@mui/material';
import Checkbox from '@mui/material/Checkbox';
import { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { useRecoilState } from 'recoil';
import { userInfo } from '../../../recoil';
import Radio from '@mui/material/Radio';
import RadioGroup from '@mui/material/RadioGroup';
import FormControlLabel from '@mui/material/FormControlLabel';
import FormControl from '@mui/material/FormControl';
import { TextField } from '@mui/material';

const innerTitle = ['일일권 구매', '일일권 선물'];
const innerTab = 'gift';
const url = {
  buy: '/user/tickets/purchase',
  gift: '/user/tickets/gift'
};

const UserTicketGift = () => {
  const [user, setUser] = useRecoilState(userInfo);
  const [checked, setChecked] = useState(false);
  const [isLackOfMoney, setIsLackOfMoney] = useState(false);
  // 이용권 radio state
  const [value, setValue] = useState('1');
  const [phoneNumber, setPhoneNumber] = useState('');
  const phoneNumberHandler = (e) => {
    setPhoneNumber(e.target.value);
  };
  const onChangeHandler = (event) => {
    setValue(event.target.value);
  };
  const existMoney = 0;
  const ticketPrice = 1000;
  const afterMoney = existMoney - ticketPrice;
  const lackNotice = (
    <h1 className={styles.moneyLack}>보유 금액이 부족합니다!</h1>
  );
  const navigate = useNavigate();
  const movePageHandler = () => {
    navigate('/user/payment/charge');
  };
  const handleChange = (event) => {
    setChecked(event.target.checked);
  };

  useEffect(() => {
    if (ticketPrice > existMoney) {
      setIsLackOfMoney(true);
    } else {
      setIsLackOfMoney(false);
    }
  }, [existMoney, ticketPrice]);

  const onSubmitHandler = (e) => {
    e.preventDefault();
    //todo 보유금액에서 미납금액만큼 차감 후, post요청
  };
  return (
    <Layout>
      <InnerTabBar title={innerTitle} select={innerTab} url={url} />
      <Article>
        <div className={styles.notice}>
          <p>
            <span style={{ color: 'red' }}>1회 1매</span>씩 선물 가능합니다.
          </p>
          <p>
            선물받는 사용자는{' '}
            <span style={{ color: 'red' }}>
              이용권을 보유하고 있지 않아야 합니다.
            </span>
          </p>
        </div>
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
            <h2>이용권 선물하기</h2>
            <div className={styles.favorite}>
              <div className={styles.money}>
                <span>현재 보유 금액:</span>
                <span>{existMoney.toLocaleString()}원</span>
                <br />
                <span>결제 후 보유 금액:</span>
                <span>{afterMoney.toLocaleString()}원</span>
              </div>
              <h3
                style={{ margin: '5px', padding: '10px', fontSize: '1.7rem' }}
              >
                선물 받을 사용자의 번호 입력
              </h3>
              <TextField
                required
                value={phoneNumber}
                onChange={phoneNumberHandler}
                id="outlined-required"
                placeholder="번호만 입력(- 제외)"
                variant="standard"
                margin="normal"
                sx={{
                  '& input': {
                    width: '15vw',
                    height: '5vh',
                    fontSize: '1.3rem'
                  }
                }}
              />
              <div>
                <h3 style={{ marginTop: '20px', marginBottom: '10px' }}>
                  선물할 이용권 선택
                </h3>
                <FormControl>
                  <RadioGroup
                    aria-labelledby="demo-controlled-radio-buttons-group"
                    name="controlled-radio-buttons-group"
                    value={value}
                    onChange={onChangeHandler}
                  >
                    <FormControlLabel
                      value="1"
                      control={
                        <Radio
                          sx={{
                            '& .MuiSvgIcon-root': {
                              fontSize: 20
                            }
                          }}
                        />
                      }
                      label="1시간 권 (1,000원)"
                      sx={{
                        '& span': {
                          fontSize: 15
                        }
                      }}
                    />
                    <FormControlLabel
                      value="2"
                      control={
                        <Radio
                          sx={{
                            '& .MuiSvgIcon-root': {
                              fontSize: 20
                            }
                          }}
                        />
                      }
                      label="2시간 권 (2,000원)"
                      sx={{
                        '& span': {
                          fontSize: 15
                        }
                      }}
                    />
                    <FormControlLabel
                      value="24"
                      control={
                        <Radio
                          sx={{
                            '& .MuiSvgIcon-root': {
                              fontSize: 20
                            }
                          }}
                        />
                      }
                      label="24시간 권 (24,000원)"
                      sx={{
                        '& span': {
                          fontSize: 15
                        }
                      }}
                    />
                  </RadioGroup>
                </FormControl>
                <div
                  style={{
                    display: 'flex',
                    padding: '10px',
                    alignItems: 'baseline'
                  }}
                >
                  <p> 결제금액: </p>
                  <span className={styles.number}>
                    {ticketPrice.toLocaleString()}원
                  </span>
                </div>
              </div>

              <div style={{ marginTop: '20px', fontSize: '1.15rem' }}>
                <Checkbox
                  checked={checked}
                  onChange={handleChange}
                  inputProps={{ 'aria-label': 'controlled' }}
                  sx={{ margin: '0px' }}
                />
                추가요금자동결제, 환불규정, 이용약관에 동의하며 결제를
                진행합니다.
              </div>
              {isLackOfMoney && (
                <div style={{ display: 'flex', alignItems: 'center' }}>
                  {lackNotice}
                  <Button
                    variant="contained"
                    color="primary"
                    sx={{ fontSize: '1rem', height: '5vh' }}
                    onClick={movePageHandler}
                  >
                    충전 페이지로 이동
                  </Button>
                </div>
              )}
              <div className={styles.inputBox}>
                <Button
                  variant="contained"
                  color="primary"
                  sx={{ fontSize: '1.3rem' }}
                  disabled={!checked || isLackOfMoney}
                  onClick={onSubmitHandler}
                >
                  이용권 결제
                </Button>
              </div>
            </div>
          </Card>
        </Container>
      </Article>
    </Layout>
  );
};

export default UserTicketGift;
