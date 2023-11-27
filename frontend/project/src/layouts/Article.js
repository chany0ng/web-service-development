import styles from './Article.module.scss';
const Article = ({ children }) => {
  return <article className={styles.article}>{children}</article>;
};

export default Article;
