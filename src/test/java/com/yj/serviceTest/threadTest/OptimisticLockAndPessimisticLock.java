package com.yj.serviceTest.threadTest;

/**
 * 数据库中的乐观锁和悲观锁
 * 悲观锁对数据操作持悲观态度，认为数据很容易被其他线程修改，所以在整个数据处理过程中，使用数据库
 * 的排他锁，避免其他线程修改数据，等到自己的线程提交事务后释放锁
 *
 * 乐观锁认为数据在一般情况下不会造成冲突，访问记录时不会加排他锁，而是在数据提交更新时，使用
 * 类似CAS操作对数据冲突与否进行检测。
 * 乐观锁不会使用数据库提供的锁机制，一般在表中添加version字段或使用业务状态来实现。
 */
public class OptimisticLockAndPessimisticLock {

    class EntryObject{
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public EntryObject query(String sql,long id){
        return new EntryObject();
    }

    public int update(String sql,EntryObject entry){
        return 1;
    }

    public String generatorName(EntryObject entry){
        return entry.toString();
    }

    public int updateEntryByPessimisticLock(long id){
        //（1）使用悲观锁获取指定记录
        EntryObject entry=query("select * from table1 where id=#{id} for update",id);

        //(2)修改记录内容，根据计算修改entry记录的属性
        String name=generatorName(entry);
        entry.setName(name);
        //...

        //(3)update操作
        int count=update("update table1 set name=#{name},age=#{age} where id=#{id}",entry);
        return count;
    }

    public boolean updateEntryByOptimisticLock(long id){
        boolean result=false;
        int retryNum=5;
        while(retryNum>0){
            //(1.1)使用乐观锁获取指定记录
            EntryObject entry=query("select * from table1 where id=#{id}",id);

            //(2.1)修改记录内容，version字段不能被修改
            String name=generatorName(entry);
            entry.setName(name);
            //。。。

            //（3.1）update操作
            int count=update("update table1 set name=#{name},age=#{age},version=${version}+1 " +
                    "where id=#{id} and version=#{version}",entry);

            if(count==1){
                result=true;
                break;
            }
            retryNum--;
        }
        return result;
    }

}
