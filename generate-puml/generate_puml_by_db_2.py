from sqlalchemy import create_engine, MetaData, Table
from sqlalchemy.engine.reflection import Inspector
from sqlalchemy import inspect
from jinja2 import Environment, FileSystemLoader
import os

# -----------------------
# CONFIGURAÇÕES
# -----------------------
DB_URI = "mysql+pymysql://tiago.pereira_read_NRGHu:g4aXNn5Q-GWJ430lPQHy@hotpay-db-read.vulcano.rocks:3306/hotpay"
OUTPUT_FILE = "output_2.puml"
TABLES = ["transaction_subscription_extra_info", "transaction_item_recurrence_history"]

# -----------------------
# CONEXÃO COM BANCO
# -----------------------
engine = create_engine(DB_URI)
metadata = MetaData()
inspector = inspect(engine)

# -----------------------
# COLETA DADOS DAS TABELAS
# -----------------------
tables_info = []

for table_name in TABLES:
    table = Table(table_name, metadata, autoload_with=engine)
    columns = []
    fk_columns = {fk['constrained_columns'][0] for fk in inspector.get_foreign_keys(table_name)}

    for col in table.columns:
        columns.append({
            "name": col.name,
            "primary_key": col.primary_key,
            "is_fk": col.name in fk_columns
        })

    tables_info.append({
        "name": table_name,
        "columns": columns
    })

# -----------------------
# RELACIONAMENTOS
# -----------------------
relationships = []

for table_name in TABLES:
    fks = inspector.get_foreign_keys(table_name)
    for fk in fks:
        referred_table = fk['referred_table']
        if referred_table in TABLES:
            relationships.append({
                "left": referred_table,
                "right": table_name,
                "label": None  # pode adicionar fk['constrained_columns'][0] aqui se quiser
            })

# -----------------------
# GERAÇÃO COM JINJA2
# -----------------------
env = Environment(loader=FileSystemLoader(searchpath="."))
template = env.get_template("template.puml.j2")

rendered = template.render(tables=tables_info, relationships=relationships)

with open(OUTPUT_FILE, "w") as f:
    f.write(rendered)

print(f"✅ Arquivo '{OUTPUT_FILE}' gerado com sucesso!")
