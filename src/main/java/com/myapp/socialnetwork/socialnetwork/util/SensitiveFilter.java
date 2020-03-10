package com.myapp.socialnetwork.socialnetwork.util;

import org.apache.commons.lang3.CharUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

@Component
public class SensitiveFilter {

    private static final Logger logger = LoggerFactory.getLogger(SensitiveFilter.class);

    //替换符
    private static final String REPLACEMENT = "***";

    //根节点
    private TrieNode rootNode = new TrieNode();

    @PostConstruct
    public void init() {
        try (
                InputStream is = this.getClass().getClassLoader().getResourceAsStream("sensitive-words.txt");
                BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        )
                {
            String keyword;
            while((keyword = reader.readLine())!=null){
                // 添加前缀树
                this.addKeyword(keyword);
            }
        } catch (IOException e){
            logger.error("加载敏感词失败：" + e.getMessage());
        }

    }

    // 将敏感词添加到前缀树中去
    private void addKeyword(String keyword) {
        //新指针
        TrieNode tempNode = rootNode;
        for(int i = 0; i< keyword.length(); i++) {
            char c = keyword.charAt(i);
            TrieNode subNode = tempNode.getSubNode(c);
            if (subNode == null) {
                // 初始化子节点
                subNode = new TrieNode();
                tempNode.addSubNode(c, subNode);
            }

            //指针指向下一节点 进入下一轮循环
            tempNode = subNode;

            // 结束时 设置敏感词标志
            if(i == keyword.length()-1) {
                tempNode.setKeywordEnd(true);
            }
        }
    }

    /**
     * 过滤敏感词
     *
     * @param text 带过滤的文本
     * @return 过滤后的文本
     */
    public String filter(String text) {
        if (StringUtils.isBlank(text)) {
            return null;
        }

        // 指针一 前缀树
        TrieNode tempNode = rootNode;
        // 指针二 字符串 字符串片段开头
        int begin = 0;
        // 指针三 字符串片段结尾
        int position = 0;
        // result
        StringBuilder sb = new StringBuilder();

        while(begin < text.length()) {
            if( position < text.length()){
                Character c = text.charAt(position);

                // 跳过符号
                if(isSymbol(c)){
                    // 如果指针1处于根节点 计入结果
                    if(tempNode == rootNode) {
                        sb.append(c);
                        position++;
                    }
                    // 无论在哪，指针3向下走
                    position++;
                    continue;

                }

                //检查下级节点
                tempNode = tempNode.getSubNode(c);
                if(tempNode ==null) {
                    sb.append(text.charAt(begin));
                    position = ++begin;
                    tempNode = rootNode;
                }
                else if(tempNode.isKeywordEnd()){
                    sb.append(REPLACEMENT);
                    begin = ++position;
                }
                else {
                    position++;
                }

            }
            // position 越界
            else {
                sb.append(text.charAt(begin));
                position = begin++;
                tempNode = rootNode;
            }

        }
        return sb.toString();
    }

    private boolean isSymbol(Character c) {
        return !CharUtils.isAsciiAlphanumeric(c) && (c < 0x2E80 || c > 0x9FFF);
    }

    // 前缀树
    private class TrieNode {
        // 关键词结束标识
        private boolean isKeywordEnd = false;

        // 子节点(key下级字符，value下级节点）
        private Map<Character, TrieNode> subNodes = new HashMap<>();

        public boolean isKeywordEnd() {
            return isKeywordEnd;
        }

        public void setKeywordEnd(boolean keywordEnd) {
            isKeywordEnd = keywordEnd;
        }

        // 添加子节点
        public void addSubNode(Character c, TrieNode node) {
            subNodes.put(c,node);
        }

        //获取子节点
        public TrieNode getSubNode(Character c) {
            return subNodes.get(c);
        }
    }
}
