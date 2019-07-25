# um_auto_down
UM 기상장 자동 다운로드를 위한 모듈

- Java jsch 라이브러리의 channelSftp를 이용하여 SFTP 접속 및 파일 다운로드
- XML 형식의 파라미터를 POST 방식으로 요청받아 다운로드 할 파일 목록을 파악 후 다운로드 진행
- 파일 유효성 검사 (기상장 파일의 단일면, 등압면 쌍 파악) 후 통과한 파일만 targetFolder로 이동
- 계산서버의 기상파일 요청에 해당하는 파일 제공 기능을 하는 REST API 구현
