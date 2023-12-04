import styles from './PostView.module.scss';
import CustomButton from '../Button/CustomButton';
const PostView = ({ data, moveToList }) => {
  const realDate = new Date(data.date).toLocaleDateString();
  return (
    <article className={styles.article}>
      <div>
        <h2 style={{ textAlign: 'left', fontSize: '1.8rem' }}>{data.title}</h2>
        <p style={{ textAlign: 'left' }}>{realDate}</p>
        <p style={{ textAlign: 'left' }}>조회수: {data.view}</p>
      </div>
      <div>
        {data.content}
        data.content Lorem ipsum dolor sit amet consectetur adipisicing elit.
        <br />
        Soluta deleniti molestias maxime nesciunt ipsam maiores ea assumenda
        <br />
        quidem perferendis laboriosam cum atque a sunt id doloremque laudantium
        velit, magni adipisci.
      </div>
      <CustomButton name="목록" onClick={moveToList} />
    </article>
  );
};

export default PostView;
