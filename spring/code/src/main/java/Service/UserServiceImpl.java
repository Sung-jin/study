package Service;

import DAO.UserDao;
import DTO.User;
import Domain.Level;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

import java.util.List;

public class UserServiceImpl implements UserService, UserLevelUpgradePolicy {
    static final int MIN_LOGCOUNT_FOR_SILVER = 50;
    static final int MIN_RECOOMEND_FOR_GOLD = 30;

    private UserDao userDao;
    private MailSender mailSender;

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    public void setMailSender(MailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void add(User user) {
        if (user.getLevel() == null) user.setLevel(Level.BASIC);
        userDao.add(user);
    }

    public void upgradeLevels() {
        List<User> users = userDao.getAll();
        for (User user : users) {
            if (canUpgradeLevel(user)) {
                upgradeLevel(user);
            }
        }
    }

    public boolean canUpgradeLevel(User user) {
        Level currentLevel = user.getLevel();
        switch (currentLevel) {
            case BASIC: return (user.getLogin() >= MIN_LOGCOUNT_FOR_SILVER);
            case SILVER: return (user.getRecommend() >= MIN_RECOOMEND_FOR_GOLD);
            case GOLD: return false;
            default: throw new IllegalArgumentException("Unknown Level : " + currentLevel);
        }
    }

    public void upgradeLevel(User user) {
        user.upgradeLevel();
        userDao.update(user);
        sendUpgradeEmail(user);
    }

    private void sendUpgradeEmail(User user) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(user.getEmail());
        mailMessage.setFrom("useradmin@example.com");
        mailMessage.setSubject("Upgrade 안내");
        mailMessage.setText("사용자님의 등급이 " + user.getLevel().name() + "로 업그레이드 되었습니다.");

        this.mailSender.send(mailMessage);
        // 지금 이대로라면 만약 DB 작업도중 실패하여 롤백하게 되어도 DB문제가 발생하기 전까지의 사용자들에게는 메일이 날아가고 롤백되어지는 문제가 있다.
        // 이를 해결하기에는 다음과 같은 방법들이 있다.
        // 1. 메일 전송해야 하는 사용자의 리스트를 가지고 있다가 DB 작업이 다 끝났을 때 일괄적으로 전송
        // 2. 메일 전송 기능에 트랜잭션 기능을 구현하여 사용
    }
}
