package com.cloud.user.dao.es;

import com.alibaba.fastjson2.JSON;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.apache.http.HttpHost;
import org.apache.lucene.index.IndexOptions;
import org.apache.lucene.queryparser.xml.builders.BooleanQueryBuilder;
import org.apache.lucene.search.FuzzyQuery;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.client.indices.GetIndexResponse;
import org.elasticsearch.cluster.metadata.MappingMetadata;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.FuzzyQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.SortOrder;

import java.util.Map;

public class ESTest {
    static RestHighLevelClient client = new RestHighLevelClient(
            RestClient.builder(new HttpHost("localhost", 9200))
    );
    public static void main(String[] args) throws Exception {
        aggQuery();
    }

    public static void aggQuery() throws Exception {
        SearchRequest request = new SearchRequest();
        request.indices("users");

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        AggregationBuilder aggregationBuilder = AggregationBuilders.min("minTel").field("age");
        //AggregationBuilder aggregationBuilder2 = AggregationBuilders.max("tel");

        searchSourceBuilder.aggregation(aggregationBuilder);
        request.source(searchSourceBuilder);
        SearchResponse response = client.search(request, RequestOptions.DEFAULT);
        System.out.println(response);
        System.out.println(response.getHits().getTotalHits());
        for (SearchHit hits : response.getHits()) {
            System.out.println(hits.getSourceAsString());
        }
        client.close();
    }

    public static void conditionQuery() throws Exception {
        SearchRequest request = new SearchRequest();
        request.indices("users");
        //条件
        SearchSourceBuilder searchSourceBuilder =  SearchSourceBuilder.searchSource();


        BoolQueryBuilder booleanQueryBuilder = QueryBuilders.boolQuery();
        //多组条件  mast/mustnot  should
        booleanQueryBuilder.must(QueryBuilders.matchQuery("sex", "male"));
        booleanQueryBuilder.should(QueryBuilders.matchQuery("tel", "1500"));

        //范围查询  gt lt gte lte
        RangeQueryBuilder rangeQueryBuilder = QueryBuilders.rangeQuery("tel");
        rangeQueryBuilder.gte("1500");

        //模糊查询
        //FuzzyQueryBuilder fuzziness = QueryBuilders.fuzzyQuery("name", "张").fuzziness(Fuzziness.ONE);

        //高亮
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        highlightBuilder.preTags("<front color='red'>");
        highlightBuilder.postTags("</front>");
        highlightBuilder.field("name");

        //单组条件
        //searchSourceBuilder .query(QueryBuilders.termQuery("sex", "male"));
        //searchSourceBuilder.query(fuzziness);
        searchSourceBuilder.query(booleanQueryBuilder);
        searchSourceBuilder.query(rangeQueryBuilder);
        searchSourceBuilder.highlighter(highlightBuilder);
        //分页
        searchSourceBuilder.from(0);
        searchSourceBuilder.size(4);
        //排序
        searchSourceBuilder.sort("tel", SortOrder.DESC);
        //过滤字段
        searchSourceBuilder.fetchSource(new String[]{"name", "tel"}, new String[]{});
        request.source(searchSourceBuilder);
        SearchResponse response = client.search(request, RequestOptions.DEFAULT);
        System.out.println(response);
        System.out.println(response.getHits().getTotalHits());
        for (SearchHit hits : response.getHits()) {
            System.out.println(hits.getSourceAsString());
        }
        client.close();
    }


    public static void query() throws Exception {
        SearchRequest request = new SearchRequest();
        request.indices("users");
        request.source(SearchSourceBuilder.searchSource().query(QueryBuilders.matchAllQuery()));
        SearchResponse response = client.search(request, RequestOptions.DEFAULT);
        System.out.println(response.getHits().getTotalHits());
        for (SearchHit hits : response.getHits()) {
            System.out.println(hits.getSourceAsString());
        }
        client.close();
    }

    public static void batchDel() throws Exception {
        BulkRequest request = new BulkRequest();
        request.add(new DeleteRequest().index("users").id("1003"));
        request.add(new DeleteRequest().index("users").id("1004"));
        request.add(new DeleteRequest().index("users").id("1005"));

        BulkResponse response = client.bulk(request, RequestOptions.DEFAULT);
        System.out.println(response);
        client.close();
    }

    //批量
    public static void batchAdd() throws Exception {
        BulkRequest request = new BulkRequest();
        request.add(new IndexRequest().index("users").id("1003").source(XContentType.JSON, "name", "wangwu", "sex", "male", "tel", "1300"));
        request.add(new IndexRequest().index("users").id("1004").source(XContentType.JSON, "name", "zhaoliu", "sex", "male", "tel", "1400"));
        request.add(new IndexRequest().index("users").id("1005").source(XContentType.JSON, "name","zabc","sex", "male","tel", "1500"));

        BulkResponse response = client.bulk(request, RequestOptions.DEFAULT);
        System.out.println(response);
        client.close();
    }

    public static void deleteDoc() throws Exception {
        DeleteRequest request = new DeleteRequest();
        request.index("users").id("2");
        DeleteResponse response = client.delete(request, RequestOptions.DEFAULT);
        System.out.println(response);
        client.close();
    }



    public static void getDoc() throws Exception {
        GetRequest request = new GetRequest();
        request.index("users").id("2");
        GetResponse response = client.get(request, RequestOptions.DEFAULT);
        //{"_index":"users","_type":null,"_id":"2","_version":6,"_seq_no":6,"_primary_term":2,"found":true,"_source":{"name":"echo1","sex":"male","tel":"1003"}}
        //{sex=male, name=echo1, tel=1003}
        System.out.println(response.getSource());
        client.close();
    }

    public static void updateDoc() throws Exception {
        UpdateRequest request = new UpdateRequest();
        request.index("users").id("2");
        request.doc(XContentType.JSON, "sex", "male", "tel", "1003");
        UpdateResponse response = client.update(request, RequestOptions.DEFAULT);
        System.out.println(response);
        client.close();
    }


    //文档操作
    public static void insertDoc() throws Exception {
        IndexRequest request = new IndexRequest();
        request.index("users").id("2");

        //创建数据
        User user = new User();
        user.setName("echo1");
        user.setSex("female");
        user.setTel("1002");

        String userJson = JSON.toJSONString(user);
        request.source(userJson, XContentType.JSON);

        IndexResponse index = client.index(request, RequestOptions.DEFAULT);
        DocWriteResponse.Result result = index.getResult();
        System.out.println(result);
        client.close();
    }


    //索引操作
    public static void deleteIndex() throws Exception {
        DeleteIndexRequest request = new DeleteIndexRequest("company");
        AcknowledgedResponse delete = client.indices().delete(request, RequestOptions.DEFAULT);
        System.out.println(delete.isAcknowledged());
        client.close();
    }

    public static void getIndex() throws Exception {
        GetIndexRequest getIndexRequest = new GetIndexRequest("users");
        GetIndexResponse indexResponse = client.indices().get(getIndexRequest, RequestOptions.DEFAULT);
        System.out.println(indexResponse.getAliases());
        String[] indeices = indexResponse.getIndices();
        System.out.println("indices: ");
        for (String i : indeices) {
            System.out.println("    " + i);
        }
        //System.out.println(indexResponse.getIndices());
        Map<String, MappingMetadata> map = indexResponse.getMappings();
        System.out.println("mappings:");
        for (Map.Entry<String, MappingMetadata> m : map.entrySet()) {
            //users→{properties={sex={type=keyword}, name={type=text}, tel={index=false, type=keyword}}}
            System.out.println("    " + m.getKey() + "→" + m.getValue().getSourceAsMap());
        }
        //System.out.println(indexResponse.getMappings());
        //{users={"index.creation_date":"1666259525915","index.number_of_replicas":"1","index.number_of_shards":"1","index.provided_name":"users","index.routing.allocation.include._tier_preference":"data_content","index.uuid":"XjS-mnZtSNeeRGgMn745xQ","index.version.created":"8040399"}}
        System.out.println(indexResponse.getSettings());
        client.close();
    }

    public static void createIndex() throws Exception {
        CreateIndexRequest createIndexRequest = new CreateIndexRequest("company");
        CreateIndexResponse createIndexResponse = client.indices().create(createIndexRequest, RequestOptions.DEFAULT);
        //响应状态
        boolean isAck = createIndexResponse.isAcknowledged();
        System.out.println("result → " + isAck);
        client.close();
    }

    @Data
    @ToString
    public static class User {
        String name;
        String sex;
        String tel;

        public User() {
        }

        public User(String name, String sex, String tel) {
            this.name = name;
            this.sex = sex;
            this.tel = tel;
        }

        public String getName() {
            return name;
        }

        public String getSex() {
            return sex;
        }

        public String getTel() {
            return tel;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public void setTel(String tel) {
            this.tel = tel;
        }

        @Override
        public String toString() {
            return "User{" +
                    "name='" + name + '\'' +
                    ", sex='" + sex + '\'' +
                    ", tel='" + tel + '\'' +
                    '}';
        }
    }
}
