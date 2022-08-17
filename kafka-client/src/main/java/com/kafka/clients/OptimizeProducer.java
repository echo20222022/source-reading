package com.kafka.clients;

import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;
import java.util.concurrent.TimeUnit;

/**
 * 优化生产端的吞吐量.
 * */
public class OptimizeProducer {

    public void send() throws Exception {

        Properties properties = new Properties();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092,localhost:9093,localhost:9094");
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.put(ProducerConfig. VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        //默认时16k，够用了
        properties.put(ProducerConfig.BATCH_SIZE_CONFIG, 16384);
        //等待时间，默认时0
        properties.put(ProducerConfig.LINGER_MS_CONFIG, 50);
        //缓冲区大小，默认时32MB，调整到64MB
        properties.put(ProducerConfig.BUFFER_MEMORY_CONFIG, 67108864);
        //设置压缩类型
        properties.put(ProducerConfig.COMPRESSION_TYPE_CONFIG, "snappy");

        KafkaProducer<String, String> producer = new KafkaProducer<String, String>(properties);
        for (int i = 0;i < 100;i ++) {
            String bigMsg = i + "日常生活: 手机号码查询 2021年放假安排 邮编查询 长途电话区号 货币汇率查询 家常菜谱大全 人民币存款利率表 下载地址转换 北京时间 大学查询 汽车车标大全 快递查询 国家地区查询 升降旗时间 常用电话号码 电费计算器 日期差计算 网速测试 数字大写转换 今日油价 个税计算器 国际天气预报 亲属关系计算 台湾邮编查询 (共31个) 占卜求签: 指纹运势查询 生男生女预测 预测吉凶 称骨算命 黄大仙灵签 六十四卦金钱课 观音灵签 诸葛神算 妈祖天后灵签 关帝灵签 吕祖灵签 车公灵签 王公祖仔灵签 月老灵签 文王神卦 灵棋经 二十八星宿算命 佛祖灵签 月老姻缘签 (共20个) 民俗文化: 老黄历 万年历 周公解梦大全 十二生肖 歇后语大全 百家姓 民间谚语 二十四节气表 历史朝代表 解密生日 名人名言名句大全 古兰经 基督教圣经 三字经 地母经 (共16个) 交通出行: 列车时刻表 全国各地车牌号查询 车辆违章查询 北京时间校准 机场三字码查询 实时交通路况 地铁线路图 车牌限行查询 火车票代售点 中国电子地图 交通标志 (共11个) 学习应用: 汉语字典 汉语词典 成语大全 在线翻译 计算器 在线输入法 圆周率 繁体字转换器 汉字拼音查询 摩尔斯电码 存储单位换算器 时间换算器 英文名 长度换算器 温度换算器 重量换算器 体积换算器 功率换算器 面积换算器 压力换算器 热量换算器 五笔字根表 区位码查询 笔画数查询 汉字部首查询 郑码编码查询 仓颉编码查询 中文电码查询 四角号码查询 英文缩写大全 组词大全 近义词 (共32个) 休闲娱乐: 号码吉凶预测 脑筋急转弯 中华谜语大全 竖排古文 火星文转换 QQ价值评估 QQ在线状态查询 外星体重 外星年龄 在线拆字 笑话大全 绕口令大全 (共15个) 站长工具: IP地址查询 密码强度检测 时间戳转换 ASCII码对照表 HTML/JS互转 BASE64加密解密 MD5加密解密 进程查询 在线编码解码 谷歌PR查询 搜狗SR查询 网站速度测试 二维码生成器 颜色代码表 HTML特殊符号 CSS在线解压缩 JS在线解压缩 HTML代码调试器 密码生成器 (共20个) 身体健康: 安全期计算器 预产期计算器 体质指数计算器 食物营养价值 粥谱大全 生星座宝宝 身高预测 血型与性格 ";
            producer.send(new ProducerRecord<String, String>("test", bigMsg), new Callback() {
                @Override
                public void onCompletion(RecordMetadata recordMetadata, Exception e) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append(recordMetadata.topic() + " ");
                    stringBuilder.append(recordMetadata.partition() + " ");
                    stringBuilder.append(recordMetadata.offset() + " ");
                    stringBuilder.append(recordMetadata.timestamp() + " ");
                    System.out.println(stringBuilder.toString());
                }
            });
        }

        //如果发送的消息比较少，就需要sleep，否则，因为sender会等待50ms，但那时producer线程就
        //退出了
        TimeUnit.SECONDS.sleep(3);
    }

    public static void main(String[] args) throws Exception {
        OptimizeProducer producer = new OptimizeProducer();
        producer.send();
    }
}
