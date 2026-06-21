# uw-gateway-center

网关管理中心。为 [uw-gateway](../uw-gateway) 提供 ACL 访问控制、流量控制、ACME 证书的配置后台与 RPC 下发，并向业务方提供运营商限速管理 SDK（[uw-gateway-client](../uw-base/uw-gateway-client)），同时聚合 ES 访问日志输出统计与仪表盘。

## 功能模块

### 访问控制（ACL）
- 基于 SAAS / 用户类型 / 用户 ID 维度的 IP 黑白名单配置（`MscAclFilter` / `MscAclFilterData`）。
- 支持审批流（申请 → 审批 → 生效），配置变更通过缓存失效实时下发到网关。
- 网关侧 `MscAclHelper.matchAclFilter` 按 userId > userType > 默认规则 优先级匹配。

### 流量控制
- 限速维度：IP / SAAS_LEVEL / USER_TYPE / USER_ID，及其带 URI 前缀的组合（见 `MscAclRateLimitType`）。
- 网关侧 `FixedWindowRateLimiter` 固定窗口限速，请求维度入口判定 + 字节维度事后回填。
- 运营商可通过 `uw-gateway-client` 的 RPC 自助设置限速（`ServiceRpcController.updateSaasRateLimit`）。

### ACME 证书管理
- 对接 Let's Encrypt 等 CA，支持阿里云/腾讯云/AWS/Cloudflare 等 DNS 与 CDN 厂商自动完成 DNS-01 challenge 与证书部署。
- 定时扫描即将过期证书（默认提前 15 天）自动续期；不支持自动续期的手工托管证书发钉钉告警。
- 网关侧 `AcmeSslNettyServerCustomizer` 支持 SNI 多域名与 http2 动态加载。

### 访问日志统计
- 从 ES 聚合访问日志，产出全局统计、SAAS 分组统计、分时指标、状态码分布与 Top 排行。
- 定时任务（`AccessSaasStatsCroner` / `AccessGlobalStatsCroner`）每日落库昨日统计。

## 接口分层

| 角色 | 路径前缀 | 说明 |
|------|---------|------|
| RPC | `/rpc/gateway`、`/rpc/service` | 网关/客户端拉取配置、运营商自助限速（`UserType.RPC`） |
| SAAS | `/saas/...` | 运营商查看自身访问日志/统计（强制 saasId 隔离） |
| ADMIN | `/admin/...` | 租户管理员 |
| OPS | `/ops/...` | 运营（含 ACL 审批、ACME 证书管理） |
| ROOT | `/root/...` | 超级管理员 |

## 配置项

配置前缀 `uw.gateway.center`：

| 配置项 | 说明 |
|--------|------|
| `uw.gateway.center.center-name` | 系统名称（用于钉钉通知标题），默认"网关管理中心" |
| `uw.gateway.center.center-site` | 系统外网访问地址 |
| `uw.gateway.center.access-log-es.*` | 访问日志 ES 配置（server/username/password/超时） |
| `uw.gateway.center.notify-ding.*` | 通知类钉钉（notifyUrl + notifyKey） |
| `uw.gateway.center.alert-ding.*` | 报警类钉钉（notifyUrl + notifyKey） |

## 数据表

| 表 | 说明 |
|----|------|
| `msc_acl_filter` / `msc_acl_filter_data` | IP 访问控制规则与 IP 段数据 |
| `msc_acl_rate` | 流量控制规则 |
| `msc_acme_account` / `msc_acme_domain` / `msc_acme_cert` / `msc_acme_deploy` / `msc_acme_deploy_log` | ACME 账号/域名/证书/部署/部署日志 |
| `access_global_stats` / `access_saas_stats` | 全局/SAAS 访问统计 |
| `sys_crit_log` / `sys_data_history` | 关键操作日志与数据变更历史 |

## 相关模块

- [uw-gateway](../uw-gateway)：消费配置的网关本体。
- [uw-gateway-client](../uw-base/uw-gateway-client)：运营商限速管理客户端 SDK。
