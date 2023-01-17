package service;

import dao.UserDao;
import domain.Level;
import domain.User;

import javax.sql.DataSource;
import java.util.List;

public class UserService {
    public static final int MIN_LOG_COUNT_FOR_SILVER = 50;
    public static final int MIN_RECOMMEND_FOR_GOLD = 30;

    private DataSource dataSource;
    private MailSender mailSender;
    PlatformTransactionManager transactionManager;
    UserDao userDao;

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }
    public void setTransactionManager(PlatformTransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }
    public void setMailSender(MailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void upgradeLevels() {
        TransactionStatus status = transactionManager.getTransactionManager(new DefaultTransactionDefinition());

//        TransactionSynchronizationManager.initSynchronization();
//        Connection c = DataSourceUtils.getConnection(dataSource);
//        c.setAutoCommit(false);

        try {
            List<User> users = userDao.getAll();
            for (User user : users) {
                if (canUpgradeLevel(user)) {
                    upgradeLevel(user);
                }
            }

//            c.commit();
            transactionManager.commit();
        } catch (Exception e) {
//            c.rollback();
            transactionManager.rollback(status);
        } finally {
//            DataSourceUtils.releaseConnection(c, dataSource);
//            TransactionSynchronizationManager.unbindResource(this.dataSource);
//            TransactionSynchronizationManager.clearSynchronization();
        }
    }

    protected void upgradeLevel(User user) {
        user.upgradeLevel();
        userDao.update(user);
        sendUpgradeEmail(user);
    }

    private boolean canUpgradeLevel(User user) {
        Level currentLevel = user.getLevel();
        return switch (currentLevel) {
            case BASIC -> (user.getLogin() >= MIN_LOG_COUNT_FOR_SILVER);
            case SILVER -> (user.getRecommend() >= MIN_RECOMMEND_FOR_GOLD);
            case GOLD -> false;
            default -> throw new IllegalArgumentException("Unknown Level: " + currentLevel);
        };
    }

    public void add(User user) {
        if (user.getLevel() == null) user.setLevel(Level.BASIC);
        userDao.add(user);
    }

    public void sendUpgradeEmail(User user) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(user.getEmail());
        mailMessage.setFrom("mail.ksug.org");
        mailMessage.setSubject("Upgrade 안내");
        mailMessage.setText("사용자님의 등급이 " + user.getLevel().name() + " 로 업그레이드 되었습니다.");

        mailSender.send(mailMessage);
    }
}
