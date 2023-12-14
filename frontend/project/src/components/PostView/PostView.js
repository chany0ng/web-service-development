import styles from './PostView.module.scss';
import CustomButton from '../Button/CustomButton';
import { Button } from '@mui/material';
import { useNavigate } from 'react-router-dom';
import { postFetch } from '../../config';
const PostView = ({ data, moveToList, author }) => {
  const navigate = useNavigate();
  const deleteHandler = async () => {
    try {
      const response = await postFetch('api/admin/notice/delete', {
        notice_id: data.notice_id
      });
      if (response.status === 200) {
        alert('/admin/notice/list/1');
      } else if (response.status === 401) {
        navigate('/');
      } else {
        throw new Error('게시물 삭제 에러');
      }
    } catch (error) {
      console.error(error);
      alert(error);
    }
  };
  const realDate = new Date(data.date).toLocaleDateString();
  return (
    <article className={styles.article}>
      <div>
        <h2 style={{ textAlign: 'left', fontSize: '1.8rem' }}>
          {data.title}
          {data.author && (
            <Button
              variant="contained"
              color="primary"
              sx={{
                fontSize: '1rem',
                width: '5vw',
                margin: '10px',
                float: 'right'
              }}
              onClick={deleteHandler}
            >
              글 삭제
            </Button>
          )}
        </h2>
        <p style={{ textAlign: 'left' }}>{realDate}</p>
        <p style={{ textAlign: 'left' }}>조회수: {data.views}</p>
      </div>
      <div>{data.content}</div>

      <CustomButton name="목록" onClick={moveToList} />
    </article>
  );
};

export default PostView;
