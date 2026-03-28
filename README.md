# 小米便签 (Xiaomi Notes)

山西大学软件工程课程作业 —— 小米便签 Android 应用

## 功能介绍

- 📝 **新建便签**：点击右下角"+"按钮，进入编辑页面创建便签
- ✏️ **编辑便签**：点击便签卡片，进入编辑页面修改内容
- 🗑️ **删除便签**：长按便签卡片，或在编辑页面点击删除图标
- 💾 **自动保存**：返回主页面时自动保存便签内容
- 📱 **本地存储**：使用 Room 数据库持久化保存便签数据

## 技术栈

- **语言**：Java
- **数据库**：Room (SQLite)
- **架构**：MVVM (ViewModel + LiveData + Repository)
- **UI**：RecyclerView、CardView、FloatingActionButton、Material Design
- **最低 SDK**：Android 5.0 (API 21)

## 项目结构

```
app/src/main/java/com/xiaomi/notes/
├── Note.java              # 便签数据模型 (Room Entity)
├── NoteDao.java           # 数据访问对象接口
├── NoteDatabase.java      # Room 数据库
├── NoteRepository.java    # 数据仓库层
├── NoteViewModel.java     # ViewModel
├── NotesAdapter.java      # RecyclerView 适配器
├── MainActivity.java      # 主界面（便签列表）
└── NoteEditActivity.java  # 编辑界面
```

## 运行环境

- Android Studio Hedgehog (2023.1.1) 或更高版本
- Gradle 8.4
- Android SDK 34