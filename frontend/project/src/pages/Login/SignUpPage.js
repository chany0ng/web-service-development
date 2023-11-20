import Avatar from '@mui/material/Avatar';
import Button from '@mui/material/Button';
import TextField from '@mui/material/TextField';
import { Link } from 'react-router-dom';
import Grid from '@mui/material/Grid';
import Box from '@mui/material/Box';
import LockOutlinedIcon from '@mui/icons-material/LockOutlined';
import Typography from '@mui/material/Typography';
import Container from '@mui/material/Container';
import { MenuItem } from '@mui/material';
import LoginBackground from '../../components/Background/LoginBackground';
import { useState, useReducer } from 'react';
import { useNavigate } from 'react-router-dom';
import { postData } from '../../config';

const passwordQuestion = [
  {
    question: 'school',
    description: '졸업한 초등학교 이름'
  },
  {
    question: 'school2',
    description: '졸업한 중학교 이름'
  }
];

const SignUpPage = () => {
  const navigate = useNavigate();
  const [inputData, setInputData] = useState({
    email: '',
    user_id: '',
    password: '',
    passwordCheck: '',
    pw_question: '',
    pw_answer: '',
    phone_number: ''
  });
  const [isValid, setIsValid] = useState(true);
  const inputDataHandler = (e) => {
    e.preventDefault();
    const key = e.target.id || 'pw_question';
    const value = e.target.value;
    setInputData((prevData) => ({ ...prevData, [key]: value }));
  };

  const onSubmitHandler = async (e) => {
    try {
      e.preventDefault();
      console.log(inputData.password.trim());
      console.log(inputData.passwordCheck.trim());
      if (
        inputData.user_id.trim() === '' ||
        inputData.password.trim() === '' ||
        inputData.passwordCheck.trim() !== inputData.password.trim() ||
        inputData.pw_question.trim() === '' ||
        inputData.pw_answer.trim() === '' ||
        inputData.phone_number.trim() === '' ||
        !inputData.email.includes('@')
      ) {
        setIsValid(false);
      } else {
        setIsValid(true);
        const { status } = await postData('url', inputData);
        if (status) {
          alert('회원가입 성공');
          navigate('/signin');
        }
      }
    } catch (error) {
      console.error('회원가입 에러', error);
    }
  };
  return (
    <LoginBackground>
      <Container component="main" maxWidth="sm" style={{ marginTop: '150px' }}>
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
            <LockOutlinedIcon />
          </Avatar>
          <Typography component="h2" variant="h4">
            회원 가입
          </Typography>
          <Box component="form" noValidate sx={{ mt: 3 }}>
            <Grid container spacing={2}>
              <Grid item xs={12}>
                <TextField
                  value={inputData.email}
                  onChange={inputDataHandler}
                  required
                  fullWidth
                  id="email"
                  label="Email Address"
                  variant="standard"
                  name="email"
                />
              </Grid>
              <Grid item xs={12}>
                <TextField
                  value={inputData.id}
                  onChange={inputDataHandler}
                  required
                  fullWidth
                  name="user_id"
                  id="user_id"
                  label="Id"
                  type="id"
                  variant="standard"
                />
              </Grid>
              <Grid item xs={12}>
                <TextField
                  value={inputData.password}
                  onChange={inputDataHandler}
                  required
                  fullWidth
                  name="password"
                  label="Password"
                  type="password"
                  id="password"
                  variant="standard"
                />
              </Grid>
              <Grid item xs={12}>
                <TextField
                  value={inputData.passwordCheck}
                  onChange={inputDataHandler}
                  required
                  fullWidth
                  name="passwordCheck"
                  label="Password Check"
                  type="password"
                  id="passwordCheck"
                  variant="standard"
                />
              </Grid>
              <Grid item xs={12}>
                <TextField
                  value={inputData.pw_question}
                  onChange={inputDataHandler}
                  required
                  fullWidth
                  name="pw_question"
                  id="pw_question"
                  select
                  label="Password Question"
                  variant="standard"
                  helperText="비밀번호 찾기 질문을 선택해주세요"
                >
                  {passwordQuestion.map((option) => (
                    <MenuItem key={option.question} value={option.description}>
                      {option.description}
                    </MenuItem>
                  ))}
                </TextField>
              </Grid>
              <Grid item xs={12}>
                <TextField
                  value={inputData.pw_answer}
                  onChange={inputDataHandler}
                  required
                  fullWidth
                  variant="standard"
                  name="pw_answer"
                  label="Password Answer"
                  id="pw_answer"
                  helperText="비밀번호 찾기 답변을 작성해주세요"
                />
              </Grid>
              <Grid item xs={12}>
                <TextField
                  value={inputData.phone_number}
                  onChange={inputDataHandler}
                  required
                  fullWidth
                  variant="standard"
                  name="phone_number"
                  label="Phone Number( - 없이 숫자만 입력)"
                  type="tel"
                  id="phone_number"
                />
              </Grid>
              {!isValid && (
                <Grid item xs={12} sx={{ color: 'error.main' }}>
                  입력을 다시 확인해주세요!
                </Grid>
              )}
            </Grid>
            <Button
              onClick={onSubmitHandler}
              type="submit"
              fullWidth
              variant="contained"
              sx={{ mt: 3, mb: 2 }}
            >
              Sign Up
            </Button>
            <Grid container justifyContent="center">
              <Grid item xs={4}>
                <Link
                  to="/findpw"
                  style={{ textDecoration: 'none', color: 'blue' }}
                >
                  비밀번호를 잊으셨나요?
                </Link>
              </Grid>
              <Grid item xs={4}>
                <Link
                  to="/signin"
                  style={{ textDecoration: 'none', color: 'blue' }}
                >
                  이미 계정을 가지고 계신가요?
                </Link>
              </Grid>
            </Grid>
          </Box>
        </Box>
      </Container>
    </LoginBackground>
  );
};

export default SignUpPage;