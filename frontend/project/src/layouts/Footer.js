import React from 'react';
import styles from './Footer.module.scss';

const Footer = () => {
  return (
    <footer>
      {/* 프로젝트 설명과 링크 */}
      <div style={{ display: 'flex', justifyContent: 'space-around' }}>
        <div>
          <h3>About Project</h3>
          <p>데이터베이스 및 데이터 시각화 과목의 프로젝트 결과물입니다</p>
          <p>
            서울시에서 운영하는 무인공공자전거 대여 서비스인 '따릉이'의 open
            API를 이용하여 개발하였습니다.
          </p>
        </div>

        {/* 유튜브 링크와 깃허브 링크 */}
        <div>
          <h3>About Link</h3>
          <div className={styles.link}>
            <div>
              <p>프로젝트에 대한 설명이 포함된 영상</p>
              <a
                href="https://www.youtube.com"
                target="_blank"
                rel="noreferrer"
              >
                Youtube Link
              </a>
            </div>
            <div>
              <p>프로젝트 관련 소스 코드</p>
              <a
                href="https://github.com/chany0ng/web-service-development"
                target="_blank"
                rel="noreferrer"
              >
                Github Link
              </a>
            </div>
            <div>
              <p>DB로 사용한 공공 데이터 정보</p>
              <a
                href="https://data.seoul.go.kr/dataList/OA-15493/A/1/datasetView.do"
                target="_blank"
                rel="noreferrer"
              >
                서울 열린데이터 광장
              </a>
            </div>
          </div>
        </div>
      </div>

      {/* 저작권 정보 */}
      <div className={styles.copyright}>
        <p style={{ textAlign: 'center' }}>
          © {new Date().getFullYear()} Kwangwoon University. Computer
          Information Engineering. All rights reserved.
        </p>
      </div>
    </footer>
  );
};

export default Footer;
