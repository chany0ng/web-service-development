import Layout from '../../../layouts/Layout';
import Article from './../../../layouts/Article';
import InnerTabBar from './../../../components/TabBar/InnerTabBar';
import CustomButton from '../../../components/Button/CustomButton';
import { useNavigate } from 'react-router-dom';
import Box from '@mui/material/Box';
import Typography from '@mui/material/Typography';
import Modal from '@mui/material/Modal';
import { useState } from 'react';
import { postFetch } from '../../../config';

const style = {
  position: 'absolute',
  top: '50%',
  left: '50%',
  transform: 'translate(-50%, -50%)',
  width: 400,
  bgcolor: 'background.paper',
  border: '1px solid #000',
  boxShadow: 24,
  p: 4
};
const innerTitle = ['공지사항', '고장 신고'];
const innerTab = 'report';
const url = {
  notice: '/user/notice/noticeList/1',
  report: '/user/report/reportList/1'
};
const UserReportWrite = () => {
  const navigate = useNavigate();
  const [open, setOpen] = useState(false);
  const handleOpen = () => {
    // 여기에 fetch함수 호출
    setOpen(true);
  };
  const handleClose = () => {
    setOpen(false);
    navigate('/user/notice/noticeList/1');
  };
  const [reportData, setReportData] = useState({
    bike_id: '123456',
    content: ''
  });
  const fetchPost = async () => {
    try {
      const response = await postFetch('api/report', reportData);
      const data = await response.text();
      if (response.status === 200) {
        console.log(data);
        const realDate = new Date(data.date).toLocaleDateString();
        setReportData((prevData) => ({
          ...prevData,
          ...data,
          date: realDate
        }));
      } else if (response.status === 401) {
        navigate('/');
      } else {
        throw new Error(`게시물 정보 로드 실패: ${response.status}`);
      }
    } catch (error) {
      console.error(error);
      alert(error);
    }
  };
  return (
    <Layout>
      <InnerTabBar title={innerTitle} select={innerTab} url={url} />
      <Article>
        <CustomButton name="고장 신고 하기" onClick={handleOpen} />
        <Modal
          open={open}
          onClose={handleClose}
          aria-labelledby="modal-modal-title"
          aria-describedby="modal-modal-description"
        >
          <Box sx={style}>
            <Typography id="modal-modal-title" variant="h4" component="h2">
              고장신고가 접수되었습니다
            </Typography>
            <Typography
              id="modal-modal-description"
              sx={{ mt: 2 }}
              variant="h5"
            >
              고장 신고 내용 :
            </Typography>
          </Box>
        </Modal>
      </Article>
    </Layout>
  );
};

export default UserReportWrite;
