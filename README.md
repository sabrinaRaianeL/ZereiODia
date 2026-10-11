# Zerei O Dia 📝

**Zerei O Dia** é um aplicativo Android nativo desenvolvido para gerenciamento intuitivo e eficiente de tarefas diárias (To-Do List). O projeto utiliza interface nativa com **XML Layouts**, **Material Design 3**, arquitetura **MVVM** e persistência de dados com **Room Database**.

---

## 🎯 Objetivo do Aplicativo

O principal objetivo do **Zerei O Dia** é ajudar os usuários a organizarem seu dia a dia de forma simples e produtiva. O aplicativo permite criar, acompanhar, concluir e deletar tarefas com facilidade, além de permitir o agendamento de datas limite (*Due Date*) para cada atividade.

---

## ✨ Funcionalidades

- **➕ Adição de Tarefas**: Crie tarefas rapidamente informando o título e, opcionalmente, uma data de conclusão.
- **📅 Seletor de Data (Material Date Picker)**: Escolha datas com facilidade utilizando o componente oficial do Material Design.
- **✅ Conclusão de Tarefas**: Marque e desmarque tarefas concluídas. O aplicativo destaca tarefas finalizadas aplicando um efeito visual de texto riscado (*strikethrough*).
- **🗑️ Exclusão Segura**: Remova tarefas com uma caixa de diálogo de confirmação (*MaterialAlertDialogBuilder*) para evitar exclusões acidentais.
- **💾 Persistência de Dados**: Todas as tarefas são salvas localmente no dispositivo via **Room Database**, garantindo que seus dados permaneçam salvos mesmo após fechar o app.
- **📱 Interface Adaptativa (Edge-to-Edge)**: Design moderno ajustado às barras de sistema (*System Bars*) do Android.

---

## 🛠️ Tecnologias Utilizadas

- **Linguagem**: [Kotlin](https://kotlinlang.org/)
- **Arquitetura**: MVVM (*Model-View-ViewModel*) com *Repository Pattern* integrado à camada de dados (`data/`)
- **Interface de Usuário (UI)**:
  - XML Layouts & View Binding
  - Material Design 3 (`com.google.android.material`)
  - `RecyclerView` & `ListAdapter` (com `DiffUtil` para otimização de listas)
- **Persistência de Dados**:
  - [Room Database](https://developer.android.com/training/data-storage/room)
  - [KSP (Kotlin Symbol Processing)](https://kotlinlang.org/docs/ksp-overview.html)
- **Componentes do Android Jetpack**:
  - `ViewModel` & `LiveData`
  - `ViewBinding`
  - `Lifecycle` & `Activity-KTX`
- **Gerenciamento de Dependências**:
  - Gradle Kotlin DSL (`build.gradle.kts`)
  - Version Catalog (`gradle/libs.versions.toml`)

---

## 📂 Estrutura do Projeto

```text
com.example.zereiodia/
├── adapter/
│   └── TaskAdapter.kt              # Adapter para renderização dos itens da RecyclerView
├── data/
│   ├── AppDatabase.kt              # Classe de banco de dados Room (Singleton)
│   ├── TaskDao.kt                  # Interface com as operações de banco de dados (CRUD)
│   └── TaskRepository.kt           # Repositório que abstrai a fonte de dados para o ViewModel
├── model/
│   └── Task.kt                     # Entidade de dados (Room Entity)
├── view/
│   └── MainActivity.kt             # Activity principal responsável pela interface gráfica
└── viewmodel/
    ├── TodoViewModel.kt            # ViewModel com a lógica de negócios e gerenciamento de estado
    └── TodoViewModelFactory.kt     # Factory para instanciação do TodoViewModel com suas dependências
```

---

## ⚙️ Configuração do Ambiente e Instalação

### 📋 Pré-requisitos

Para compilar e executar o projeto, você precisará de:

1. **Android Studio**: Recomendado Android Studio Ladybug (2024.2.1) ou superior.
2. **JDK**: Java Development Kit 11 ou superior (geralmente embutido no Android Studio).
3. **Android SDK**:
   - `compileSdk`: 35
   - `minSdk`: 24 (Android 7.0 Nougat ou superior)
   - `targetSdk`: 35
4. **Dispositivo/Emulador**: Dispositivo físico Android ou Emulador configurado com API 24+.

---

### 🚀 Passo a Passo para Executar

1. **Clonar o Repositório**:
   ```bash
   git clone https://github.com/seu-usuario/ZereiODia.git
   cd ZereiODia
   ```

2. **Abrir no Android Studio**:
   - Abra o **Android Studio**.
   - Selecione **Open** e navegue até a pasta onde o repositório foi clonado.

3. **Sincronizar as Dependências (Gradle Sync)**:
   - Aguarde o Android Studio baixar as dependências automaticamente.
   - Caso não inicie sozinho, clique no botão **Sync Project with Gradle Files** (ícone do elefante no canto superior direito).

4. **Executar o Aplicativo**:
   - Conecte um dispositivo Android via USB com a *Depuração USB* ativada, ou inicie um Emulador (*Android Virtual Device*).
   - Clique no botão verde **Run 'app'** (ou pressione `Shift + F10`).