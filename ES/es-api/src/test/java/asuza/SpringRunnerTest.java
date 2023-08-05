package asuza;

import asuza.pojo.User;
import com.alibaba.fastjson.JSON;
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
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;


import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;


/**
 * @author Azusa-Yuan
 * @description
 * @github <a href="https://github.com/Azusa-Yuan">...</a>
 * @Copyright Azusa-Yuan
 */
@SpringBootTest
public class SpringRunnerTest {

    @Autowired
    @Qualifier("restHighLevelClient")
    private RestHighLevelClient client;

    // 测试索引的创建
    @Test
    void testCreateIndex() throws IOException {
        // 1、创建索引请求 相当于put
        CreateIndexRequest request = new CreateIndexRequest("ice_index");
        // 2、客户端执行请求  请求后获得响应
        CreateIndexResponse createIndexResponse = client.indices().create(request, RequestOptions.DEFAULT);

        System.out.println(createIndexResponse);
    }

    // 测试获取索引，只能判断其是否存在
    @Test
    void testExistIndex() throws IOException {
        // 1.创建索引请求
        GetIndexRequest request = new GetIndexRequest("ice_index");
        // 2.获取索引，是否存在
        boolean exists = client.indices().exists(request, RequestOptions.DEFAULT);
        System.out.println(exists);
    }

    // 测试删除索引
    @Test
    void testDeleteIndex() throws IOException {
        // 1.创建删除索引请求
        DeleteIndexRequest request = new DeleteIndexRequest("ice_index");
        // 2.获取删除状态
        AcknowledgedResponse delete = client.indices().delete(request, RequestOptions.DEFAULT);
        System.out.println(delete.isAcknowledged());
    }

    // 测试添加文档
    @Test
    void testAddDocument() throws IOException {
        // 1.创建对象
        User user = new User("ice",3);
        // 2.创建请求
        IndexRequest request = new IndexRequest("ice_index");
        // 3.规则 put /ice_index/_doc/1
        request.id("5");
        request.timeout(TimeValue.timeValueSeconds(1));// 或者 request.timeout("1s");
        // 4.把数据放入请求 json
        request.source(JSON.toJSONString(user), XContentType.JSON);
        // 5.客户端发送请求，获取响应结果
        IndexResponse indexResponse = client.index(request, RequestOptions.DEFAULT);
        System.out.println(indexResponse.toString());// 结果的json
        System.out.println(indexResponse.status());// 对应我们命令返回的状态 created
    }

    // 获取文档的信息
    @Test
    void testGetDocument() throws IOException {
        GetRequest getRequest = new GetRequest("ice_index", "3");
        GetResponse getResponse = client.get(getRequest, RequestOptions.DEFAULT);
        System.out.println(getResponse.getSourceAsString());// 打印文档的内容
        System.out.println(getResponse);// 返回的全部内容和密令一样
    }

    // 更新文档的信息
    @Test
    void testUpdateDocument() throws IOException {
        UpdateRequest updateRequest = new UpdateRequest("ice_index", "1");
        updateRequest.timeout("1s");
        User user = new User("狂神说Java", 18);
        updateRequest.doc(JSON.toJSONString(user),XContentType.JSON);
        UpdateResponse update = client.update(updateRequest, RequestOptions.DEFAULT);
        System.out.println(update.status());
    }

    // 删除文档记录
    @Test
    void testDeleteRequest() throws IOException {
        DeleteRequest deleteRequest = new DeleteRequest("ice_index","1");
        deleteRequest.timeout("1s");
        DeleteResponse deleteResponse = client.delete(deleteRequest, RequestOptions.DEFAULT);
        System.out.println(deleteResponse.status());
    }

    // 特殊的，真实的项目一般都会批量插入数据
    @Test
    void testBulkRequest() throws IOException {
        BulkRequest bulkRequest = new BulkRequest();
        bulkRequest.timeout("1s");
        ArrayList<User> users = new ArrayList<>();
        users.add(new User("kuangshen1",3));
        users.add(new User("kuangshen2",3));
        users.add(new User("kuangshen3",3));
        users.add(new User("qinjiang1",3));
        users.add(new User("qinjiang1",3));
        users.add(new User("qinjiang1",3));
        for (int i = 0; i < users.size(); i++) {
            bulkRequest.add(new IndexRequest("kuang_index")
                    .id(""+(i+1))// 不设置id，则会生成默认id
                    .source(JSON.toJSONString(users.get(i)),XContentType.JSON));
        }
        BulkResponse bulkResponse = client.bulk(bulkRequest, RequestOptions.DEFAULT);
        System.out.println(bulkResponse.hasFailures());// 是否失败，返回false代表成功
    }

    // 查询
    // SearchRequest 搜索请求
    // SearchSourceBuilder 条件构造
    // HighlightBuilder 构建高亮
    // TermQueryBuilder精确查询
    // MatchALLQueryBuilder
    // xxx QueryBuilder 对应我们刚才看到的命令！
    @Test
    void testSearch() throws IOException {
        // 创建请求
        SearchRequest searchRequest = new SearchRequest("ice_index");
        // 构建搜索条件
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        // 查询条件，我们可以使用 QueryBuilders 工具来实现
        TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery("name", "qinjiang1");// 精确
        //MatchAllQueryBuilder matchAllQueryBuilder = QueryBuilders.matchAllQuery();// 匹配所有
        sourceBuilder.query(termQueryBuilder);
        sourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));
        searchRequest.source(sourceBuilder);
        SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
        System.out.println(JSON.toJSONString(searchResponse.getHits()));
        System.out.println("=============");
        for (SearchHit hit : searchResponse.getHits().getHits()) {
            System.out.println(hit.getSourceAsMap());
        }
    }
}
