package com.example.data

import com.example.model.Badge
import com.example.model.EmotionModule
import com.example.model.QuizCircuit
import com.example.model.QuizQuestion
import com.example.model.StoryChapter

object QuizRepository {

    // --- MÓDULO 1: O MOTOR DA NOSSA CABEÇA (Capítulo Principal) ---
    private val module1StoryChapters = listOf(
        StoryChapter(
            id = 1,
            moduleId = "modulo_1_motor_da_mente",
            title = "O Motor Ligado no 220V",
            emoji = "🏎️",
            summary = "Você já reparou como o nosso dia a dia parece uma corrida maluca?",
            content = "A gente costuma achar que quem ganha mais pontos é quem faz mais coisas: tirar notas boas, passar de fase no jogo, ir bem no treino e cumprir todas as tarefas.\n\nPor causa disso, ficamos com a cabeça ligada no 220V o tempo todo, cobrando um super desempenho da gente mesmo!",
            secretTip = "Fazer lições e brincar é ótimo, mas você tem valor pelo que você É, não apenas pelo que você FAZ!",
            accentColorHex = 0xFFFF7043
        ),
        StoryChapter(
            id = 2,
            moduleId = "modulo_1_motor_da_mente",
            title = "A Correria e o Hipercontrole",
            emoji = "🔍",
            summary = "Para ter certeza de que tudo vai dar certo, a gente tenta controlar tudo ao redor...",
            content = "Tem gente que quer até controlar a vida dos amigos ou dos irmãos, procurando onde tá o erro e olhando cada detalhezinho. Às vezes, a gente até deixa de dormir ou de brincar só para tentar resolver e controlar mais coisas!\n\nMas ó, viver nessa correria toda tem um preço: uma tensãozinha chata e permanente, e aquela insatisfação de achar que a vida de agora não é tão legal quanto deveria ser.",
            secretTip = "O amanhã ainda não chegou. Aproveite o recreio e a brincadeira de agora!",
            accentColorHex = 0xFFFFB300
        ),
        StoryChapter(
            id = 3,
            moduleId = "modulo_1_motor_da_mente",
            title = "As Falsas Saídas: Telas & Guloseimas",
            emoji = "🎮",
            summary = "Assistir YouTube, jogar sem parar ou comer doces realmente relaxa a cabeça?",
            content = "A gente logo pensa que assistir a vídeos no YouTube, jogar videogame ou maratonar séries vai ser o bastante para relaxar. Outras vezes, a gente desconta comendo um monte de guloseimas ou pedindo brinquedos novos...\n\nSó que tudo isso roda no piloto automático e mantém o motor da mente girando sem parar! Não é tão simples desacelerar só com distrações.",
            secretTip = "Distração não é descanso verdadeiro. A mente precisa de espaço para respirar!",
            accentColorHex = 0xFF7E57C2
        ),
        StoryChapter(
            id = 4,
            moduleId = "modulo_1_motor_da_mente",
            title = "Mudando de Motorista: Ação vs Ser",
            emoji = "🧘",
            summary = "Modo 'Fazer, Pensar e Controlar' vs. Modo 'Ser e Estar'.",
            content = "O grande segredo é que nossa mente adora o modo 'fazer, pensar e controlar'. Ele é ótimo para lição de casa e organizar a mochila.\n\nMas precisamos dar espaço para o modo 'Ser e Estar'! É quando você se conecta com o momento presente, fica de boa com o que sente e curte o agora. Nele, a sua essência assume a direção e você relaxa de verdade!",
            secretTip = "Quando você come um sorvete sentindo o sabor bem devagar, você está no modo Ser e Estar!",
            accentColorHex = 0xFF26A69A
        ),
        StoryChapter(
            id = 5,
            moduleId = "modulo_1_motor_da_mente",
            title = "As Duas Super Estratégias",
            emoji = "🌟",
            summary = "1️⃣ Descarregue no Papel 📝 e 2️⃣ Viagem para Dentro 🚀",
            content = "1️⃣ Descarregue a mente no papel: Pegue um caderno ou bloco e anote o que preocupa e o que tem para fazer. O cérebro sabe que nada será esquecido e o modo 'fazer' ganha autorização para tirar uma folga!\n\n2️⃣ Faça uma viagem para dentro de você: Uma meditação 'Ser e Estar' te ajuda a fazer uma pausa especial, respirar fundo e se conectar com sua essência!",
            secretTip = "Experimente as ferramentas interativas na aba de ferramentas do app!",
            accentColorHex = 0xFF5C6BC0
        )
    )

    private val module1Circuits = listOf(
        QuizCircuit(
            id = 1,
            moduleId = "modulo_1_motor_da_mente",
            title = "A Corrida Maluca",
            subtitle = "A mente ligada no 220V!",
            emoji = "🏎️",
            colorHex = 0xFFFF7043,
            questions = listOf(
                QuizQuestion(
                    id = 101,
                    circuitId = 1,
                    question = "Por que muitas crianças sentem a cabeça ligada no 220V no dia a dia?",
                    options = listOf(
                        "Porque achamos que ganha mais pontos quem faz, faz e faz sem parar!",
                        "Porque o cérebro tem baterias de brinquedo",
                        "Porque beber água gela o cérebro rápido demais"
                    ),
                    correctIndex = 0,
                    explanation = "Isso mesmo! A gente costuma achar que quem faz mais coisas (tirar nota boa, passar de fase, treinar) é quem ganha mais pontos na vida.",
                    funTip = "O cérebro não é uma máquina de corrida ininterrupta. Ele precisa de pausas!"
                ),
                QuizQuestion(
                    id = 102,
                    circuitId = 1,
                    question = "Cobrar um 'super desempenho' de si mesmo o tempo todo causa o quê?",
                    options = listOf(
                        "Transforma a pessoa num robô invisível",
                        "Cansaço mental e a sensação de que nunca é o suficiente",
                        "Gera dinheiro infinito para comprar brinquedos"
                    ),
                    correctIndex = 1,
                    explanation = "Exato! Quando nos cobramos demais o tempo todo, ficamos esgotados e achamos que nada do que fizemos foi bom o bastante.",
                    funTip = "Comemorar o que você já fez com carinho acalma o motor da cabeça!"
                )
            )
        ),
        QuizCircuit(
            id = 2,
            moduleId = "modulo_1_motor_da_mente",
            title = "A Armadilha do Controle",
            subtitle = "Querendo mandar em tudo!",
            emoji = "🕹️",
            colorHex = 0xFFFFB300,
            questions = listOf(
                QuizQuestion(
                    id = 201,
                    circuitId = 2,
                    question = "O que acontece quando tentamos controlar tudo ao redor, até a vida dos amigos ou irmãos?",
                    options = listOf(
                        "Todo mundo faz exatamente o que a gente manda com um sorriso",
                        "A gente deixa de dormir ou brincar só para procurar erros e resolver coisas",
                        "A nossa lição de casa se faz sozinha por mágica"
                    ),
                    correctIndex = 1,
                    explanation = "Certíssimo! Tentar controlar cada detalhe e o que vai acontecer amanhã rouba nosso sono e a hora de brincar.",
                    funTip = "Não dá para controlar o amanhã. O único momento que podemos viver é o agora!"
                ),
                QuizQuestion(
                    id = 202,
                    circuitId = 2,
                    question = "Qual é o 'preço' de viver nessa correria tentando controlar tudo?",
                    options = listOf(
                        "Uma tensão chata e permanente, insatisfação e prejuízo nas amizades",
                        "Ficar com dentes de vampiro",
                        "Perder o apetite para comer sorvete no calor"
                    ),
                    correctIndex = 0,
                    explanation = "Muito bem! O texto ensina que a tensão chata prejudica nossa saúde, amizades e faz a vida de agora parecer sem graça.",
                    funTip = "Soltar o controle alivia os ombros e a mente!"
                )
            )
        ),
        QuizCircuit(
            id = 3,
            moduleId = "modulo_1_motor_da_mente",
            title = "Falsos Atalhos para Relaxar",
            subtitle = "Telas, doces e automático",
            emoji = "📺",
            colorHex = 0xFF7E57C2,
            questions = listOf(
                QuizQuestion(
                    id = 301,
                    circuitId = 3,
                    question = "Assistir a vídeos no YouTube ou jogar videogame sem parar realmente desliga o motor da mente?",
                    options = listOf(
                        "Sim, o cérebro apaga completamente e dorme",
                        "Não, porque roda no automático e mantém o motor da mente girando a mil!",
                        "Desliga somente se o celular estiver na tomada"
                    ),
                    correctIndex = 1,
                    explanation = "Perfeito! As telas e redes sociais distraem, mas o cérebro continua trabalhando em ritmo acelerado no piloto automático.",
                    funTip = "Descanso de verdade é dar silêncio e calma para a sua essência."
                ),
                QuizQuestion(
                    id = 302,
                    circuitId = 3,
                    question = "Comer muitas guloseimas ou pedir brinquedos novos acalma a mente por dentro?",
                    options = listOf(
                        "Apenas dá um alívio temporário, mas logo a insatisfação volta",
                        "Cura qualquer preocupação para sempre",
                        "Faz o cérebro aprender a meditar sozinho"
                    ),
                    correctIndex = 0,
                    explanation = "Isso aí! Comer guloseimas ou pedir coisas novas é só uma tentativa de fuga rápida. O motor interno continua precisando desacelerar.",
                    funTip = "A verdadeira calma vem de dentro, não da prateleira da loja!"
                )
            )
        ),
        QuizCircuit(
            id = 4,
            moduleId = "modulo_1_motor_da_mente",
            title = "Mudando de Motorista",
            subtitle = "Modo Ação vs. Modo Ser e Estar",
            emoji = "🧘",
            colorHex = 0xFF26A69A,
            questions = listOf(
                QuizQuestion(
                    id = 401,
                    circuitId = 4,
                    question = "Para que serve o Modo 'Ação' (Fazer, Pensar e Controlar)?",
                    options = listOf(
                        "É inútil e devemos jogar fora",
                        "É super útil para lições de casa, organizar mochila e estratégias de jogos!",
                        "Serve apenas para correr mais rápido que um leopardo"
                    ),
                    correctIndex = 1,
                    explanation = "Exatamente! O modo ação é ótimo e importante, só não pode ficar dirigindo o tempo inteiro sem descanso.",
                    funTip = "O modo 'Ação' é o copiloto; ele precisa de pausas para não pifar!"
                ),
                QuizQuestion(
                    id = 402,
                    circuitId = 4,
                    question = "O que é o maravilhoso Modo 'Ser e Estar'?",
                    options = listOf(
                        "Conectar com o momento presente, ficar de boa com o que sente e curtir o agora",
                        "Fingir que é uma estátua e não falar com ninguém",
                        "Dormir durante a aula de matemática"
                    ),
                    correctIndex = 0,
                    explanation = "Show de bola! 'Ser e Estar' é estar presente com o corpo e a respiração, sentindo paz e aproveitando o instante atual.",
                    funTip = "Quando o 'Ser e Estar' assume a direção, o motor da cabeça relaxa de verdade."
                )
            )
        ),
        QuizCircuit(
            id = 5,
            moduleId = "modulo_1_motor_da_mente",
            title = "Os Superpoderes da Calma",
            subtitle = "Papel 📝 e Viagem Interior 🚀",
            emoji = "🌟",
            colorHex = 0xFF5C6BC0,
            questions = listOf(
                QuizQuestion(
                    id = 501,
                    circuitId = 5,
                    question = "Por que 'Descarregar a mente no papel' 📝 dá tanto alívio no cérebro?",
                    options = listOf(
                        "Porque o cérebro sabe que nada será esquecido e o modo 'fazer' ganha folga!",
                        "Porque o papel engole os pensamentos e some com eles",
                        "Porque canetas mágicas leem a mente sozinhas"
                    ),
                    correctIndex = 0,
                    explanation = "Sensacional! Anotar preocupações e lições impede de ficar remoendo problemas e autoriza o cérebro a relaxar.",
                    funTip = "Um diário emocional também é ótimo para desabafar quando estiver bravo ou confuso."
                ),
                QuizQuestion(
                    id = 502,
                    circuitId = 5,
                    question = "O que é a 'Viagem para dentro de você' 🚀?",
                    options = listOf(
                        "Comprar uma passagem de foguete para a Lua",
                        "Uma meditação 'Ser e Estar' para pausar e se conectar com sua essência interior",
                        "Engolir uma bússola mágica"
                    ),
                    correctIndex = 1,
                    explanation = "Campeão! É uma pausa especial de respiração e presença para acalmar o motor e se reconectar com quem você é de verdade.",
                    funTip = "Apenas alguns minutos respirando com calma transformam o seu dia!"
                )
            )
        )
    )

    // --- MÓDULO 2: A TEMPESTADE DAS EMOÇÕES ---
    private val module2StoryChapters = listOf(
        StoryChapter(
            id = 201,
            moduleId = "modulo_2_tempestade_das_emocoes",
            title = "O Vulcão da Raiva",
            emoji = "🌋",
            summary = "Por que sentimos que vamos explodir e como podemos esfriar a lava?",
            content = "Sentir raiva não faz de ninguém uma pessoa má! A raiva é como um alarme de incêndio que avisa quando algo parece injusto ou fora do lugar.\n\nO segredo não é engolir a lava, mas aprender a respirar antes de explodir para não queimar as amizades!",
            secretTip = "Quando a raiva vier, conte 4 respirações lentas antes de falar ou agir.",
            accentColorHex = 0xFFE53935
        ),
        StoryChapter(
            id = 202,
            moduleId = "modulo_2_tempestade_das_emocoes",
            title = "A Chuva da Tristeza",
            emoji = "🌧️",
            summary = "A tristeza não é um defeito: ela pede colo e descanso.",
            content = "Quando ficamos tristes, é como se o dia ficasse nublado e sem energia. Querer forçar um sorriso falso o tempo todo cansa demais.\n\nChorar e desabafar com alguém de confiança é como uma chuva passageira: ela limpa o céu para o sol voltar a brilhar!",
            secretTip = "Depois da chuva de lágrimas, o solo do coração fica fértil e calmo de novo.",
            accentColorHex = 0xFF1E88E5
        )
    )

    private val module2Circuits = listOf(
        QuizCircuit(
            id = 21,
            moduleId = "modulo_2_tempestade_das_emocoes",
            title = "O Resfriador de Vulcões",
            subtitle = "Lidando com a raiva sem explodir",
            emoji = "🌋",
            colorHex = 0xFFE53935,
            questions = listOf(
                QuizQuestion(
                    id = 2101,
                    circuitId = 21,
                    question = "Sentir raiva de vez em quando é normal?",
                    options = listOf(
                        "Sim! Todas as emoções são normais; o importante é o que fazemos com ela",
                        "Não, pessoas boas nunca sentem raiva",
                        "A raiva só existe em desenhos animados"
                    ),
                    correctIndex = 0,
                    explanation = "Exatamente! Sentir raiva faz parte da vida de todo mundo. O superpoder é aprender a respirar para não machucar ninguém nem a si mesmo.",
                    funTip = "Dizer 'estou com raiva agora, preciso de um minuto' é sinal de muita inteligência!"
                ),
                QuizQuestion(
                    id = 2102,
                    circuitId = 21,
                    question = "O que fazer quando sentir o calor da raiva subindo no corpo?",
                    options = listOf(
                        "Quebrar um brinquedo para mostrar força",
                        "Fazer uma pausa, respirar fundo e pedir um tempo para se acalmar",
                        "Gritar bem alto na cara do amigo"
                    ),
                    correctIndex = 1,
                    explanation = "Muito bem! Fazer uma pausa rápida dá tempo para o cérebro pensar com clareza antes de tomar uma atitude.",
                    funTip = "A respiração profunda é o extintor de incêndio natural do corpo."
                )
            )
        ),
        QuizCircuit(
            id = 22,
            moduleId = "modulo_2_tempestade_das_emocoes",
            title = "O Barco da Calma",
            subtitle = "Navegando pela tristeza e pelo medo",
            emoji = "⛵",
            colorHex = 0xFF1E88E5,
            questions = listOf(
                QuizQuestion(
                    id = 2201,
                    circuitId = 22,
                    question = "Por que desabafar e falar sobre o que estamos sentindo ajuda tanto?",
                    options = listOf(
                        "Porque nomear o sentimento tira o poder do medo e nos faz sentir acolhidos",
                        "Porque faz o relógio andar mais rápido",
                        "Porque assim ganhamos ingressos para o cinema"
                    ),
                    correctIndex = 0,
                    explanation = "Perfeito! Quando colocamos em palavras o que sentimos para quem confiamos, o peso no peito diminui.",
                    funTip = "Dividir uma dor com alguém que te ama faz a dor cair pela metade!"
                )
            )
        )
    )

    // --- MÓDULO 3: O DETETIVE DOS PENSAMENTOS ---
    private val module3StoryChapters = listOf(
        StoryChapter(
            id = 301,
            moduleId = "modulo_3_detetive_dos_pensamentos",
            title = "Pensamentos não são Fatos!",
            emoji = "🕵️‍♂️",
            summary = "A nossa mente adora inventar histórias que nem sempre são verdade.",
            content = "Às vezes a cabeça sussurra: 'Ninguém gosta de mim' ou 'Eu nunca vou conseguir aprender isso'.\n\nMas adivinha só: só porque você pensou algo, não significa que seja uma verdade absoluta! Um bom detetive investiga as provas antes de acreditar.",
            secretTip = "Pergunte a si mesmo: 'Isso é um fato real comprovado ou é apenas um pensamento passageiro?'",
            accentColorHex = 0xFF6D4C41
        )
    )

    private val module3Circuits = listOf(
        QuizCircuit(
            id = 31,
            moduleId = "modulo_3_detetive_dos_pensamentos",
            title = "A Lupa da Verdade",
            subtitle = "Investigando o que a mente fala",
            emoji = "🔍",
            colorHex = 0xFF6D4C41,
            questions = listOf(
                QuizQuestion(
                    id = 3101,
                    circuitId = 31,
                    question = "Se você pensar 'Eu nunca vou conseguir passar dessa fase ou aprender isso', isso é uma verdade?",
                    options = listOf(
                        "Não! É só um pensamento de frustração; com treino e paciência você consegue!",
                        "Sim, se pensou, virou lei no universo",
                        "Pensamentos sempre têm razão"
                    ),
                    correctIndex = 0,
                    explanation = "Isso mesmo! Pensamentos automáticos são como nuvens passando no céu da mente. Nem todos dizem a verdade.",
                    funTip = "Troque 'eu não consigo' por 'eu ainda estou aprendendo'!"
                )
            )
        )
    )

    // --- MÓDULO 4: A FÁBRICA DA EMPATIA & AMIZADE ---
    private val module4StoryChapters = listOf(
        StoryChapter(
            id = 401,
            moduleId = "modulo_4_fabrica_da_empatia",
            title = "Colocando os Óculos do Outro",
            emoji = "👓",
            summary = "Como entender o que o amigo está sentindo sem julgar.",
            content = "Empatia é como colocar um par de óculos mágicos para enxergar o mundo com os olhos de outra pessoa.\n\nQuando um amigo estiver chateado ou bravo, em vez de brigar, tente imaginar: 'O que será que ele está sentindo por dentro agora?'",
            secretTip = "Ouvir com os ouvidos e com o coração é o maior presente da amizade.",
            accentColorHex = 0xFF00897B
        )
    )

    private val module4Circuits = listOf(
        QuizCircuit(
            id = 41,
            moduleId = "modulo_4_fabrica_da_empatia",
            title = "O Superpoder da Escuta",
            subtitle = "Entendendo os amigos de verdade",
            emoji = "🤝",
            colorHex = 0xFF00897B,
            questions = listOf(
                QuizQuestion(
                    id = 4101,
                    circuitId = 41,
                    question = "O que significa ter empatia por um amigo?",
                    options = listOf(
                        "Tentar se colocar no lugar dele e entender seus sentimentos com carinho",
                        "Emprestar brinquedos apenas se ele pagar",
                        "Ignorar o que ele fala e jogar sozinho"
                    ),
                    correctIndex = 0,
                    explanation = "Perfeito! A empatia fortalece as amizades e nos ajuda a viver em harmonia.",
                    funTip = "Um abraço sincero ou uma pergunta 'como você tá?' pode transformar o dia de alguém."
                )
            )
        )
    )

    // --- LISTA GERAL DE MÓDULOS DO EMOTIONALINGO KIDS ---
    val modules: List<EmotionModule> = listOf(
        EmotionModule(
            id = "modulo_1_motor_da_mente",
            chapterNumber = 1,
            title = "O Motor da Nossa Cabeça",
            subtitle = "Modo Ação vs. Modo Ser e Estar",
            emoji = "🧠🏎️",
            summary = "A corrida maluca do dia a dia, a armadilha do controle e as 2 super estratégias para desacelerar o 220V!",
            colorHex = 0xFF5E35B1,
            storyChapters = module1StoryChapters,
            circuits = module1Circuits,
            hasInteractiveTools = true
        ),
        EmotionModule(
            id = "modulo_2_tempestade_das_emocoes",
            chapterNumber = 2,
            title = "A Tempestade das Emoções",
            subtitle = "Raiva, Tristeza e o Arco-íris da Calma",
            emoji = "⛈️🌈",
            summary = "Aprenda a reconhecer o vulcão interno, acolher a chuva das lágrimas e encontrar o barco da tranquilidade.",
            colorHex = 0xFFE53935,
            storyChapters = module2StoryChapters,
            circuits = module2Circuits,
            hasInteractiveTools = false
        ),
        EmotionModule(
            id = "modulo_3_detetive_dos_pensamentos",
            chapterNumber = 3,
            title = "O Detetive dos Pensamentos",
            subtitle = "Investigando o que a Mente Diz",
            emoji = "🕵️‍♂️🔍",
            summary = "Nem tudo o que a cabeça pensa é verdade! Descubra como usar a lupa da verdade para não cair em armadilhas.",
            colorHex = 0xFF6D4C41,
            storyChapters = module3StoryChapters,
            circuits = module3Circuits,
            hasInteractiveTools = false
        ),
        EmotionModule(
            id = "modulo_4_fabrica_da_empatia",
            chapterNumber = 4,
            title = "A Fábrica da Empatia",
            subtitle = "Amizade, Escuta e Conexão",
            emoji = "🤝💖",
            summary = "Coloque os óculos mágicos da empatia e descubra como criar laços fortes e verdadeiros com seus amigos.",
            colorHex = 0xFF00897B,
            storyChapters = module4StoryChapters,
            circuits = module4Circuits,
            hasInteractiveTools = false
        )
    )

    fun getModule(id: String): EmotionModule {
        return modules.find { it.id == id } ?: modules.first()
    }

    // Default legacy shortcuts for backward compatibility
    val circuits: List<QuizCircuit> get() = modules.flatMap { it.circuits }
    val storyChapters: List<StoryChapter> get() = getModule("modulo_1_motor_da_mente").storyChapters

    val allBadges: List<Badge> = listOf(
        Badge(
            id = "first_step",
            title = "Primeira Marcha",
            description = "Completou seu primeiro circuito do Quiz!",
            emoji = "🏁"
        ),
        Badge(
            id = "calm_pilot",
            title = "Piloto Consciente",
            description = "Acertou as perguntas sobre o motor 220V e a armadilha do controle!",
            emoji = "🧠"
        ),
        Badge(
            id = "screen_wise",
            title = "Desbravador Digital",
            description = "Descobriu que telas no automático não desligam o motor!",
            emoji = "📺"
        ),
        Badge(
            id = "zen_driver",
            title = "Mestre do Ser e Estar",
            description = "Aprendeu a mudar o motorista da mente para a tranquilidade!",
            emoji = "🧘"
        ),
        Badge(
            id = "paper_pro",
            title = "Descarregador no Papel",
            description = "Usou a ferramenta de tirar preocupações da cabeça e colocar no papel!",
            emoji = "📝"
        ),
        Badge(
            id = "space_traveler",
            title = "Viajante do Interior",
            description = "Fez uma sessão de respiração guiada para dentro de si!",
            emoji = "🚀"
        ),
        Badge(
            id = "emotion_detective",
            title = "Detetive Emocional",
            description = "Explorou novos capítulos e investigou as emoções no EmotionaLingo Kids!",
            emoji = "🕵️‍♂️"
        ),
        Badge(
            id = "grand_champion",
            title = "Mestre das Emoções",
            description = "Conquistou estrelas em todas as pistas de quiz do EmotionaLingo Kids!",
            emoji = "🏆"
        )
    )
}
