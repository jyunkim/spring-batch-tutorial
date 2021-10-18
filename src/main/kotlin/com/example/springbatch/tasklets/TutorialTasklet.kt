package com.example.springbatch.tasklets

import org.slf4j.LoggerFactory
import org.springframework.batch.core.StepContribution
import org.springframework.batch.core.scope.context.ChunkContext
import org.springframework.batch.core.step.tasklet.Tasklet
import org.springframework.batch.repeat.RepeatStatus

class TutorialTasklet : Tasklet {

    override fun execute(contribution: StepContribution, chunkContext: ChunkContext): RepeatStatus? {
        val logger = LoggerFactory.getLogger(TutorialTasklet::class.java)
        logger.debug("Tasklet executed")
        return RepeatStatus.FINISHED
    }
}