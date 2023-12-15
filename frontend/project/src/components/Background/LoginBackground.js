import './css/LoginBackground.module.min.css';
const LoginBackground = ({ children }) => {
  return (
    <div
      className="background-image"
      style={{ backgroundImage: 'url(/images/landingImage2.jpg)' }}
    >
      {children}
    </div>
  );
};

export default LoginBackground;
