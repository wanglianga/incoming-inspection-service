# 制造工厂来料检验服务

## 项目简介

本项目是一个基于 Spring Boot 的制造工厂来料检验（IQC）服务管理系统，提供采购到货登记、抽样检验、检验结果录入、不合格判定、让步接收、退货处理和入库放行等完整业务流程。

## 原始需求

> 制造工厂需要来料检验服务，Spring Boot 接口处理采购到货、抽样检验、不合格判定、让步接收、退货和入库放行。业务字段包括采购单、供应商、物料编码、批次、到货数量、抽样方案、检验项目、缺陷等级、让步原因、生产急缺程度和仓库库位。仓库收货后生成待检批次，质检录入尺寸、外观、性能和包装结果，采购联系供应商确认退换或让步，生产急用时可申请带条件接收。服务要区分轻微缺陷、重大不合格、数量短缺、证书缺失、急料让步和供应商拒绝退货。

## 技术栈

- **后端框架**: Spring Boot 3.2.0
- **数据库**: MySQL 8.0
- **ORM**: Spring Data JPA
- **构建工具**: Maven
- **JDK 版本**: 17
- **其他**: Lombok, Spring Validation

## 核心功能模块

### 1. 基础数据管理
- 供应商管理（增删改查）
- 物料管理（增删改查）
- 采购订单管理（增删改查）

### 2. 来料检验流程
- **采购到货**: 仓库收货后生成待检批次
- **抽样方案**: 根据到货数量设定抽样数量和方案
- **检验录入**: 支持尺寸(DIMENSION)、外观(APPEARANCE)、性能(PERFORMANCE)、包装(PACKAGING)、证书(CERTIFICATE)五类检验
- **缺陷记录**: 记录缺陷等级（轻微/重大/严重）和不合格类型
- **结果判定**: 自动判定合格/不合格

### 3. 不合格处理
- **让步接收申请**: 生产急用时可申请带条件接收，记录让步原因、生产急缺程度
- **让步审批**: 支持审批通过/拒绝
- **退货处理**: 创建退货单，支持供应商确认退货或拒绝退货

### 4. 入库放行
- 检验合格或让步接收批准后可执行入库
- 记录仓库库位、接收人等信息

## 业务状态流转

检验批次状态：
- `PENDING` - 待检
- `SAMPLING` - 已抽样
- `INSPECTING` - 检验中
- `QUALIFIED` - 合格
- `UNQUALIFIED` - 不合格
- `CONCESSION_PENDING` - 让步待审批
- `CONCESSION_APPROVED` - 让步批准
- `CONCESSION_REJECTED` - 让步拒绝
- `RETURN_PENDING` - 退货待确认
- `RETURNED` - 已退货
- `REJECTED_BY_SUPPLIER` - 供应商拒绝退货
- `STORED` - 已入库

不合格类型：
- `MINOR_DEFECT` - 轻微缺陷
- `MAJOR_UNQUALIFIED` - 重大不合格
- `QUANTITY_SHORTAGE` - 数量短缺
- `CERTIFICATE_MISSING` - 证书缺失
- `URGENT_CONCESSION` - 急料让步
- `SUPPLIER_REJECT_RETURN` - 供应商拒绝退货

## 启动方式

### 前置要求

- JDK 17+
- Maven 3.9+
- MySQL 8.0+

### 本地启动

#### 1. 安装依赖

```bash
mvn clean install -DskipTests
```

#### 2. 配置数据库

确保本地 MySQL 服务已启动，并创建数据库：

```sql
CREATE DATABASE incoming_inspection CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

默认数据库配置（可在 `src/main/resources/application.yml` 中修改）：
- 地址: localhost:3306
- 用户名: root
- 密码: root123

#### 3. 启动服务

```bash
mvn spring-boot:run
```

访问地址：http://localhost:8080

### Docker 一键启动（推荐）

#### 前置要求

- Docker 20.10+
- Docker Compose 2.0+

#### 启动命令

```bash
docker compose up --build
```

后台运行：

```bash
docker compose up --build -d
```

停止并清理服务：

```bash
docker compose down
```

访问地址：http://localhost:8080

MySQL 数据库信息：
- 端口: 3306
- 用户名: root
- 密码: root123
- 数据库名: incoming_inspection

## API 接口文档

### 基础响应格式

```json
{
  "code": 200,
  "message": "success",
  "data": {}
}
```

### 1. 供应商管理

| 方法 | 路径 | 说明 |
|------|------|------|
| POST | `/api/suppliers` | 创建供应商 |
| GET | `/api/suppliers/{id}` | 根据ID查询供应商 |
| GET | `/api/suppliers/code/{code}` | 根据编码查询供应商 |
| GET | `/api/suppliers` | 查询供应商列表 |
| PUT | `/api/suppliers/{id}` | 更新供应商 |
| DELETE | `/api/suppliers/{id}` | 删除供应商 |

### 2. 物料管理

| 方法 | 路径 | 说明 |
|------|------|------|
| POST | `/api/materials` | 创建物料 |
| GET | `/api/materials/{id}` | 根据ID查询物料 |
| GET | `/api/materials/code/{code}` | 根据编码查询物料 |
| GET | `/api/materials` | 查询物料列表 |
| PUT | `/api/materials/{id}` | 更新物料 |
| DELETE | `/api/materials/{id}` | 删除物料 |

### 3. 采购订单管理

| 方法 | 路径 | 说明 |
|------|------|------|
| POST | `/api/purchase-orders` | 创建采购订单 |
| GET | `/api/purchase-orders/{id}` | 根据ID查询采购订单 |
| GET | `/api/purchase-orders/orderNo/{orderNo}` | 根据单号查询采购订单 |
| GET | `/api/purchase-orders` | 查询采购订单列表 |
| PUT | `/api/purchase-orders/{id}` | 更新采购订单 |
| DELETE | `/api/purchase-orders/{id}` | 删除采购订单 |

### 4. 检验批次管理

| 方法 | 路径 | 说明 |
|------|------|------|
| POST | `/api/inspection-batches/arrival` | 采购到货登记，生成待检批次 |
| POST | `/api/inspection-batches/sampling` | 执行抽样 |
| POST | `/api/inspection-batches/{batchNo}/start` | 开始检验 |
| GET | `/api/inspection-batches/{id}` | 根据ID查询检验批次 |
| GET | `/api/inspection-batches/batchNo/{batchNo}` | 根据批次号查询 |
| GET | `/api/inspection-batches` | 查询检验批次列表 |
| GET | `/api/inspection-batches/status/{status}` | 按状态查询检验批次 |

### 5. 检验管理

| 方法 | 路径 | 说明 |
|------|------|------|
| POST | `/api/inspections/record` | 录入检验记录 |
| POST | `/api/inspections/defect` | 录入缺陷记录 |
| POST | `/api/inspections/judge/{batchNo}` | 判定检验结果 |
| GET | `/api/inspections/records/batch/{batchId}` | 查询批次检验记录 |
| GET | `/api/inspections/defects/batch/{batchId}` | 查询批次缺陷记录 |

### 6. 让步接收管理

| 方法 | 路径 | 说明 |
|------|------|------|
| POST | `/api/concessions/apply` | 申请让步接收 |
| POST | `/api/concessions/approve` | 审批让步接收 |
| GET | `/api/concessions/{id}` | 根据ID查询让步申请 |
| GET | `/api/concessions` | 查询让步申请列表 |
| GET | `/api/concessions/batch/{batchId}` | 按批次查询让步申请 |

### 7. 退货管理

| 方法 | 路径 | 说明 |
|------|------|------|
| POST | `/api/returns` | 创建退货单 |
| POST | `/api/returns/supplier-confirm` | 供应商确认退货 |
| GET | `/api/returns/{id}` | 根据ID查询退货单 |
| GET | `/api/returns/returnNo/{returnNo}` | 根据退货单号查询 |
| GET | `/api/returns` | 查询退货单列表 |
| GET | `/api/returns/batch/{batchId}` | 按批次查询退货单 |

### 8. 入库管理

| 方法 | 路径 | 说明 |
|------|------|------|
| POST | `/api/storages` | 创建入库记录 |
| GET | `/api/storages/{id}` | 根据ID查询入库记录 |
| GET | `/api/storages/storageNo/{storageNo}` | 根据入库单号查询 |
| GET | `/api/storages` | 查询入库记录列表 |
| GET | `/api/storages/batch/{batchId}` | 按批次查询入库记录 |

## 业务流程示例

### 完整检验流程

1. **创建基础数据**: 创建供应商、物料、采购订单
2. **采购到货**: 调用 `/api/inspection-batches/arrival` 生成待检批次
3. **执行抽样**: 调用 `/api/inspection-batches/sampling` 设定抽样方案
4. **录入检验结果**: 调用 `/api/inspections/record` 录入尺寸、外观、性能、包装等检验结果
5. **录入缺陷（如有）**: 调用 `/api/inspections/defect` 记录不合格项
6. **结果判定**: 调用 `/api/inspections/judge/{batchNo}` 判定合格/不合格
7. **合格批次**: 调用 `/api/storages` 执行入库
8. **不合格批次**:
   - 申请让步接收: `/api/concessions/apply` → 审批 `/api/concessions/approve` → 入库
   - 创建退货: `/api/returns` → 供应商确认 `/api/returns/supplier-confirm`

## 目录结构

```
wl-296/
├── src/
│   └── main/
│       ├── java/com/factory/inspection/
│       │   ├── IncomingInspectionApplication.java  # 启动类
│       │   ├── common/                              # 通用类
│       │   │   └── Result.java                      # 统一响应封装
│       │   ├── controller/                          # Controller 层
│       │   ├── dto/                                 # 数据传输对象
│       │   ├── entity/                              # 实体类
│       │   ├── enums/                               # 枚举类
│       │   ├── exception/                           # 异常处理
│       │   ├── repository/                          # Repository 层
│       │   └── service/                             # Service 层
│       └── resources/
│           └── application.yml                      # 应用配置
├── Dockerfile                                       # Docker 构建文件
├── docker-compose.yml                               # Docker Compose 编排
├── .dockerignore                                    # Docker 忽略文件
├── pom.xml                                          # Maven 配置
└── README.md                                        # 项目说明
```

## 注意事项

1. 本服务使用 MySQL 作为默认数据库，JPA 会自动创建表结构
2. 所有单号（批次号、退货单号、入库单号）均按时间戳+序列号自动生成
3. 检验批次有严格的状态流转控制，不允许跨状态操作
4. 让步接收和退货互斥，不合格批次只能选择其中一种处理方式
