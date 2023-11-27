import { useState } from 'react';
import Avatar from '@mui/material/Avatar';
import Button from '@mui/material/Button';
import TextField from '@mui/material/TextField';
import { Link } from 'react-router-dom';
import Grid from '@mui/material/Grid';
import Box from '@mui/material/Box';
import { Alert } from '@mui/material';
import LoginOutlinedIcon from '@mui/icons-material/LoginOutlined';
import Typography from '@mui/material/Typography';
import Container from '@mui/material/Container';
import LoginBackground from '../../components/Background/LoginBackground';
import { postData } from '../../config';
import { useNavigate } from 'react-router-dom';

const SignInPage = () => {
  const navigate = useNavigate();
  const [inputData, setInputData] = useState({
    user_id: '',
    password: ''
  });
  const [isValid, setIsValid] = useState(true);
  const inputDataHandler = (e) => {
    e.preventDefault();
    const key = e.target.id;
    const value = e.target.value;
    setInputData((prevData) => ({ ...prevData, [key]: value }));
  };

  const onSubmitHandler = async (e) => {
    try {
      e.preventDefault();
      if (inputData.user_id.trim() === '' || inputData.password.trim() === '') {
        setIsValid(false);
      } else {
        setIsValid(true);
        const { status, data } = await postData('url', inputData);
        if (status) {
          localStorage.setItem('token', data.token);
          // 로컬스토리지에 로그인상태 저장 -> 메인화면에서 useEffect로 받기
          navigate('/user/main');
        }
      }
    } catch (error) {
      alert('로그인 에러', error);
    }
  };

  return (
    <LoginBackground>
      <Container component="main" maxWidth="sm">
        <Box
          sx={{
            margin: 5,
            display: 'flex',
            flexDirection: 'column',
            alignItems: 'center'
          }}
          style={{
            padding: '20px',
            border: '1px solid black',
            borderRadius: '10px',
            backgroundColor: '#ffffff'
          }}
        >
          <Avatar sx={{ m: 1, bgcolor: 'primary.main' }}>
            <LoginOutlinedIcon fontSize="large" />
          </Avatar>
          <Typography component="h2" variant="h4">
            로그인
          </Typography>
          <Box component="form" noValidate sx={{ mt: 3 }}>
            <Grid container spacing={2}>
              <Grid item xs={12}>
                <TextField
                  value={inputData.user_id}
                  onChange={inputDataHandler}
                  fullWidth
                  name="user_id"
                  label="아이디"
                  type="id"
                  id="user_id"
                  variant="standard"
                  helperText="20자 이하"
                />
              </Grid>
              <Grid item xs={12}>
                <TextField
                  value={inputData.password}
                  fullWidth
                  onChange={inputDataHandler}
                  name="password"
                  label="비밀번호"
                  type="password"
                  id="password"
                  variant="standard"
                  helperText="20자 이하"
                />
              </Grid>

              {!isValid && (
                <Grid
                  item
                  xs={8}
                  sx={{ color: 'error.main', margin: '0 auto' }}
                >
                  <Alert
                    variant="outlined"
                    severity="error"
                    sx={{ fontSize: '1.3rem' }}
                  >
                    아이디와 비밀번호 모두 입력해주세요
                  </Alert>
                </Grid>
              )}
            </Grid>
            <Button
              type="submit"
              onClick={onSubmitHandler}
              fullWidth
              variant="contained"
              sx={{ mt: 3, mb: 2 }}
            >
              Login
            </Button>
            <Grid container justifyContent="center">
              <Grid item xs={4}>
                <Link
                  to="/signup"
                  style={{ textDecoration: 'none', color: 'blue' }}
                >
                  회원가입
                </Link>
              </Grid>
              <Grid item xs={4}>
                <Link
                  to="/findpw"
                  style={{ textDecoration: 'none', color: 'blue' }}
                >
                  비밀번호 찾기
                </Link>
              </Grid>
            </Grid>
          </Box>
        </Box>
      </Container>
    </LoginBackground>
  );
};

export default SignInPage;
