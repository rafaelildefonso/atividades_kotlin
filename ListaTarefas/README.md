# ListaTarefas

App Android (Jetpack Compose) para gerenciar tarefas, com persistência **offline-first** em **Room** e sincronização com uma API pública — arquitetura **Single Source of Truth**.

## API utilizada — JSONPlaceholder

- **Endpoint:** `GET https://jsonplaceholder.typicode.com/todos`
- **Sem cadastro/chave:** API pública e gratuita de testes.

Exemplo de resposta:

```json
{
  "userId": 1,
  "id": 1,
  "title": "delectus aut autem",
  "completed": false
}
```

### Mapeamento dos campos

| JSONPlaceholder | `Tarefa` (Room) |
|-----------------|-----------------|
| `title`         | `titulo`        |
| `completed`     | `concluido`     |
| `id`            | `remoteId` (identidade da API; `null` = tarefa local) |
| `userId`        | — (descartado)  |
| —               | `descricao` (campo local, vazio nas tarefas vindas da API) |

## Arquitetura offline-first (Single Source of Truth)

```
UI (Compose)  ──collectAsState──►  StateFlow<List<Tarefa>> + StateFlow<SyncStatus>
                                       ▲
ListaTarefasViewModel (AndroidViewModel)
                                       │
ListaTarefasRepository
  ├── observarTarefas(): Flow<List<Tarefa>>   ← lê só o Room (SSOT)
  └── suspend sincronizarTarefas()            ← Retrofit → grava no Room
                │
       TarefaDao ──► AppDatabase (Room)
       JsonPlaceholderApi (Retrofit)
```

- **A UI nunca lê da API diretamente** — apenas observa o `Flow` do Room via `StateFlow`.
- O **Repository** tem duas responsabilidades: *observar* (`Flow`) e *buscar/armazenar* (`suspend`).
- O **estado de sincronização** (`SyncStatus`: `Idle` / `Loading` / `Error`) fica separado da lista de dados; um erro de rede **não apaga** as tarefas já salvas — só exibe um aviso.
- **Persistência:** Room `@Entity` / `@Dao` / `@Database` (versão 2, com `Migration(1→2)` que preserva os dados criados na versão com `SQLiteOpenHelper`).

### Fluxo de sincronização

1. Usuário toca em **Sincronizar** → `ViewModel.sincronizar()` seta `Loading`.
2. Repository busca `GET /todos` e grava cada item no Room (upsert por `remoteId`).
3. Sucesso → `Idle`; falha de rede → `Error`, mantendo a lista local visível.
4. Depois de sincronizado, ative o **modo avião**: os dados continuam aparecendo (vêm do Room).

## Como testar

1. Abra o app e toque em **Sincronizar** (com internet).
2. Ative o modo avião → a lista continua lá (offline-first).
3. Provoque um erro de rede (ex.: sem Wi-Fi/dados) e sincronize → aparece o aviso, a lista **não** some.
4. Crie tarefas locais pelo formulário — elas coexistem com as da API (`remoteId = null`).

## Stack

- Kotlin 2.0 · Jetpack Compose · Material 3
- Room (Flow no DAO) · Retrofit + Gson
- AndroidViewModel + StateFlow (`stateIn`)
- KSP
